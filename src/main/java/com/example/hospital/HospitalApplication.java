package com.example.hospital;

import com.example.hospital.entities.Consultation;
import com.example.hospital.entities.Medecin;
import com.example.hospital.entities.Patient;
import com.example.hospital.entities.RendezVous;
import com.example.hospital.repositories.ConsultationRepository;
import com.example.hospital.repositories.MedecinRepository;
import com.example.hospital.repositories.PatientRepository;
import com.example.hospital.repositories.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class HospitalApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MedecinRepository medecinRepository;
    @Autowired
    private RendezVousRepository rendezVousRepository;
    @Autowired
    private ConsultationRepository consultationRepository;

    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Add Doctors
        List<String> list = Arrays.asList("Hassan", "Billy", "Mike");
        for (String doc : list){
            Medecin medecin = new Medecin();
            medecin.setName(doc);
            medecin.setEmail(doc + "@Med.com");
            medecin.setSpecialization(Math.random() < 0.5? "Cardiologist" : "GP");
            medecinRepository.save(medecin);
        }

        // Add Patients
        list = Arrays.asList("Toto", "Eddy", "Maddy");
        for(String name : list){
            Patient patient = new Patient();
            patient.setName(name);
            patient.setSick(Math.random() > 0.5);
            patient.setBirthDay(new Date(100 + name.length(), Calendar.FEBRUARY, 13));
            patientRepository.save(patient);
        }

        // Add RendezVous

        for (long i = 1L; i <= 3L; i++){
            RendezVous rendezVous = new RendezVous();
            rendezVous.setCancelled(Math.random() < 0.5);
            rendezVous.setDate(new Date(124, Calendar.NOVEMBER, 20 + (int) i));
            rendezVous.setPatient(patientRepository.findById(i).get());
            rendezVous.setMedecin(medecinRepository.findById(i).get());
            rendezVousRepository.save(rendezVous);
        }

        // Add Consultation

        for (long i = 1L; i <= 3L; i++) {
            Consultation consultation = new Consultation();
            consultation.setDateConsultation(new Date(124, Calendar.NOVEMBER, 20 + (int) i));
            consultation.setRapport(Math.random() < 0.5 ? "Need To do a Test" : "Good shape");
            consultation.setRendezVous(rendezVousRepository.findById(i).get());
            consultationRepository.save(consultation);
        }

        // List All Patients
        List<Patient> patients = patientRepository.findAll();
        patients.forEach( p -> {
            System.out.println(p.toString());
        });

        // Find a patient
        Patient patient = patientRepository.findById(1L).get();
        System.out.println("**********");
        System.out.println(patient.getId());
        System.out.println(patient.getName());
        System.out.println(patient.getBirthDay());
        System.out.println("**********");

        // Find patient by name
        patients = patientRepository.findByNameContains("Toto");
        patients.forEach( p -> {
            System.out.println(p.toString());
        });

        // Modify a patient
        patient = patientRepository.findById(2L).get();
        patient.setName("Yasser");
        System.out.println(patient.toString());



    }
}