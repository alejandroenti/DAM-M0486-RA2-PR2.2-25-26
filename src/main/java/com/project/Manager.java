package com.project;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import com.project.domain.ciutadans.ICiutada;
import com.project.domain.ciutats.ICiutat;

public class Manager {
    
    private static SessionFactory _factory;

    // ============================================================
    // GESTIÓ DE LA SESSIÓ
    // ============================================================

    public static void createSessionFactory() {
        try {
            _factory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException ex) { 
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex); 
        }
    }

    public static void close() {
        if (_factory != null) _factory.close();
    }

    // ============================================================
    // GESTIÓ DE TRANSACCIONS - PATRÓ DRY (Don't Repeat Yourself)
    // ============================================================

    private static void executeInTransaction(Consumer<Session> action) {
        Transaction tx = null;
        try (Session session = _factory.openSession()) {
            tx = session.beginTransaction();
            action.accept(session);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new RuntimeException("Error en transacció Hibernate", e);
        }
    }

    private static <T> T executeInTransactionWithResult(Function<Session, T> action) {
        Transaction tx = null;
        try (Session session = _factory.openSession()) {
            tx = session.beginTransaction();
            T result = action.apply(session);
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new RuntimeException("Error en transacció Hibernate", e);
        }
    }

    // ============================================================
    // OPERACIONS CRUD (Create, Read, Update, Delete)
    // ============================================================

    public static ICiutat addCiutat(String nom, String pais, int poblacio) {
        return executeInTransactionWithResult(session -> {
            ICiutat ciutat = Main.factory.createCiutat(nom, pais, poblacio);
            session.persist(ciutat);
            return ciutat;
        });
    }

    public static ICiutada addCiutada(String nom, String cognom, int edat) {
        return executeInTransactionWithResult(session -> {
            ICiutada ciutada = Main.factory.createCiutada(nom, cognom, edat);
            session.persist(ciutada);
            return ciutada;
        });
    }

    public static void updateCiutada(long ciutadaId, String name, String cognom, int edat) {
        executeInTransaction(session -> {
            ICiutada ciutada = session.get(ICiutada.class, ciutadaId);
            if (ciutada != null) {
                ciutada.setNom(name);
                session.merge(ciutada);
            }
        });
    }

    public static void updateCiutat(long ciutatId, String nom, String pais, int poblacio, Set<ICiutada> nouCiutadans) {
        executeInTransaction(session -> {
            ICiutat ciutat = session.get(ICiutat.class, ciutatId);
            if (ciutat == null) return;
            
            ciutat.setNom(nom);
            ciutat.setPais(pais);
            ciutat.setPoblacio(poblacio);
            
            // Si nouCiutadans és null, no toquem les relacions existents
            if (nouCiutadans != null) {

                // 1. Netejar ciutadans existents
                if (ciutat.getCiutadans() != null && !ciutat.getCiutadans().isEmpty()) {
                    List<ICiutada> ciutadansToRemove = List.copyOf(ciutat.getCiutadans());
                    ciutadansToRemove.forEach(ciutat::removeCiutada);
                }

                // 2. Afegir nous ciutanads (recuperant-los com a "managed")
                for (ICiutada ciutada : nouCiutadans) {
                    ICiutada managedCiutada = session.get(ICiutada.class, ciutada.getCiutadaId());
                    if (managedCiutada != null) {
                        ciutat.addCiutada(ciutada);
                    }
                }
            }

            session.merge(ciutat);
        });
    }

    public static ICiutat getCiutatWithCiutadans(long ciutatId) {
        return executeInTransactionWithResult(session -> {
            ICiutat ciutat = session.get(ICiutat.class, ciutatId);
            if (ciutat != null) {
                Hibernate.initialize(ciutat.getCiutadans());
            }
            return ciutat;
        });
    }

    public static <T> T getById(Class<T> clazz, long id) {
        return executeInTransactionWithResult(session -> session.get(clazz, id));
    }

    public static <T> void delete(Class<T> clazz, Serializable id) {
        executeInTransaction(session -> {
            T obj = session.get(clazz, id);
            if (obj != null) {
                session.remove(obj);
            }
        });
    }

    public static <T> List<T> listCollection(Class<T> clazz, String whereClause) {
        return executeInTransactionWithResult(session -> {
            String hql = "FROM " + clazz.getName();
            if (whereClause != null && !whereClause.trim().isEmpty()) {
                hql += " WHERE " + whereClause;
            }
            return session.createQuery(hql, clazz).list();
        });
    }

    public static <T> List<T> listCollection(Class<T> clazz) {
        return listCollection(clazz, "");
    }

    public static <T> String collectionToString(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        for (T obj : collection) {
            sb.append(obj.toString()).append("\n");
        }
        return sb.toString();
    }


    // ============================================================
    // CONSULTES SQL NATIVES
    // ============================================================

    public static void queryUpdate(String queryString) {
        executeInTransaction(session -> {
            NativeQuery<?> query = session.createNativeQuery(queryString, Void.class);
            query.executeUpdate();
        });
    }

    public static List<Object[]> queryTable(String queryString) {
        return executeInTransactionWithResult(session -> {
            NativeQuery<Object[]> query = session.createNativeQuery(queryString, Object[].class);
            return query.getResultList();
        });
    }
}