
package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");

        EntityManager em = emf.createEntityManager();
        System.out.println("en marcha Alberto");

        try {
            // Persistir una nueva entidad Person
            em.getTransaction().begin();

            Persona persona = Persona.builder()
                    .edad(7)
                    .nombre("Sarli").

                    build();

;

            System.out.println("IMPRIMO PERSONA ANTES  DE GRABAR");
            System.out.println("--------------------");
            System.out.println(persona);

            em.persist(persona);

           em.getTransaction().commit();

           // Cuando hace el commit pasa a estado manejado y posee un id

            System.out.println("IMPRIMO PERSONA LUEGO DE GRABAR");

            System.out.println(persona);






            // Actualizar la persona
            em.getTransaction().begin();
           persona.setEdad(979);
            persona.setNombre("Alejandro");
            em.merge(persona);
            em.getTransaction().commit();

            // Buscar la persona por ID
               Persona personaEncontrada = em.find(Persona.class, persona.getId());

            System.out.println("Persona encontrada: " + personaEncontrada);


            // Desconectar la entidad (estado Detached)
         em.getTransaction().begin();
          em.detach(persona);
           em.getTransaction().commit();

            System.out.println("Voy a eliminar persona que ya no está vinculada");
            // Eliminar la persona
              em.getTransaction().begin();
              em.remove(persona);
              em.getTransaction().commit();


           System.out.println("Me tiene que dar error");
            //Buscar la persona por ID
            Persona personaEncontrada1 = em.find(Persona.class, persona.getId());

            System.out.println("Persona encontrada desde la base de datos: " + personaEncontrada1);




            // Eliminar la persona
       //     em.getTransaction().begin();
       //     em.remove(personaEncontrada);
       //     em.getTransaction().commit();


        }catch (Exception e){

            em.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("Salí por el catch");}

        // Cerrar el EntityManager y el EntityManagerFactory
        em.close();
        emf.close();
    }
}

/*

Manejo del Ciclo de Estados en JPA
El ciclo de estados en JPA (Java Persistence API) define los diferentes estados que puede tener una entidad en relación con el contexto de persistencia (EntityManager). Comprender y manejar correctamente estos estados es crucial para trabajar eficazmente con JPA. Los estados del ciclo de vida de una entidad en JPA son:

New (Nuevo):

Una entidad está en estado "New" cuando ha sido creada pero aún no ha sido persistida en la base de datos.
Managed (Gestionado):

Una entidad está en estado "Managed" cuando está asociada con un contexto de persistencia (EntityManager) y cualquier cambio en la entidad se reflejará automáticamente en la base de datos.
Detached (Desconectado):

Una entidad está en estado "Detached" cuando ya no está asociada con un contexto de persistencia. Los cambios en la entidad no se reflejarán automáticamente en la base de datos.
Removed (Eliminado):

Una entidad está en estado "Removed" cuando ha sido marcada para su eliminación en la base de datos.
*/


