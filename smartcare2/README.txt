SMART CARE - PATIENT ADMISSION SYSTEM
=====================================

How to compile and run
----------------------
    cd smartcare
    javac *.java
    java Main

You will see seed data with these accounts:
    A001 - Admin   (Sara Admin)
    D001 - Doctor  (Dr. Ali)
    N001 - Nurse   (Lina Nurse)
    P001 - Patient (Omar Patient - cannot log in)


Design Patterns Used (mapped to assignment)
-------------------------------------------

1) SINGLETON  -> DataStore.java
   The brief says: "the data should be stored in appropriate files,
   which should have a single point of access". Singleton guarantees
   exactly one DataStore exists. Every save/load goes through
   DataStore.getInstance().

2) FACTORY    -> UserFactory.java
   The system has 4 user roles (Patient, Doctor, Nurse, Admin).
   The factory centralizes user creation so callers do not
   reference concrete classes directly. Adding a new role only
   requires editing the factory (Open/Closed principle).

3) BRIDGE     -> Person (abstraction) + Residency (implementor)
   Files: Person.java, Patient.java, Doctor.java, Nurse.java,
   Admin.java, Residency.java, JordanianResidency.java,
   NonJordanianResidency.java
   Persons and Residency types vary independently. Without the
   bridge we would need 4 x 2 = 8 classes. With the bridge we
   only need 4 + 2 = 6 and they can grow independently.

4) TEMPLATE   -> Report.java + TextReport.java + XMLReport.java
   The brief requires every report to follow the same skeleton:
   title -> summary -> content -> date + admin name.
   The template method generateReport() is final; subclasses only
   change how each section is formatted (plain text or XML).

5) PROXY      -> HospitalServiceProxy.java wrapping RealHospitalService
   Role-based access control. The proxy checks the current user's
   role before forwarding to the real service. Callers depend on
   the HospitalService interface only.


SOLID Principles Applied
------------------------
- S (Single Responsibility):
    DataStore handles only data and files.
    RealHospitalService performs operations only.
    HospitalServiceProxy handles only access control.
    Report classes only format; they don't decide content.

- O (Open / Closed):
    Adding a new MedicalProcedure type (e.g. Physiotherapy) only
    requires a new subclass; nothing in MedicalProcedure changes.
    Adding a new report format (e.g. JSONReport) only requires a
    new subclass of Report.

- L (Liskov Substitution):
    Any Patient/Doctor/Nurse/Admin can be used wherever a Person
    is expected. Any JordanianResidency or NonJordanianResidency
    can be used wherever Residency is expected.

- I (Interface Segregation):
    HospitalService exposes only what callers need.
    Residency has a tiny focused interface (two methods).

- D (Dependency Inversion):
    Main and other clients depend on the HospitalService
    interface, not on the concrete RealHospitalService or the
    Proxy. Both implement the same interface and are swappable.


File List
---------
Singleton              : DataStore.java
Factory                : UserFactory.java
Bridge - abstraction   : Person.java, Patient.java, Doctor.java,
                         Nurse.java, Admin.java
Bridge - implementor   : Residency.java, JordanianResidency.java,
                         NonJordanianResidency.java
Procedures             : MedicalProcedure.java, Medication.java,
                         LabTest.java, Radiology.java, Monitoring.java
Rooms / Sections       : Room.java, Section.java
Template               : Report.java, TextReport.java, XMLReport.java
Service / Proxy        : HospitalService.java,
                         RealHospitalService.java,
                         HospitalServiceProxy.java
Entry point            : Main.java
Quick proxy demo       : ProxyTest.java
