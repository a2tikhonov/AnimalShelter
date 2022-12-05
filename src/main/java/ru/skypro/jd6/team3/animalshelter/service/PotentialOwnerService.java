package ru.skypro.jd6.team3.animalshelter.service;

import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwner;
import ru.skypro.jd6.team3.animalshelter.repository.PotentialOwnerRepository;

import java.util.Locale;

@Service
public class PotentialOwnerService {

    private final PotentialOwnerRepository potentialOwnerRepository;

    public PotentialOwnerService(PotentialOwnerRepository potentialOwnerRepository) {
        this.potentialOwnerRepository = potentialOwnerRepository;
    }

    public void save(PotentialOwner potentialOwner) {
        potentialOwnerRepository.save(potentialOwner);
    }

    public boolean add(Long id) {
        if (find(id)) {
            return false;
        }
        PotentialOwner potentialOwner = new PotentialOwner();
        potentialOwner.setId(id);
        potentialOwner.setName("");
        potentialOwner.setPhone("");
        potentialOwnerRepository.save(potentialOwner);
        return true;
    }

    public boolean find(Long id) {
        return potentialOwnerRepository.findById(id).isPresent();
    }

    public boolean add(Long id, String message) {
        boolean nameIsCorrect = false;
        boolean phoneIsCorrect = false;
        System.out.println("message = " + message);
        String[] msg = message.split(" ");
        String name = msg[0];
        System.out.println("name = " + name);
        String phone = msg[1];
        System.out.println("phone = " + phone);
        if (!name.isBlank() && name.length() >= 2) {
            name = name.toLowerCase(Locale.ROOT);
            String firstLetter = name.substring(0, 1).toUpperCase(Locale.ROOT);
            name = firstLetter + name.substring(1);
            nameIsCorrect = true;
            System.out.println("nameIsCorrect = " + nameIsCorrect);
        }
        phone.replaceAll("\\D+", "");
        System.out.println("phone = " + phone);
        if (phone.matches("(7|8)?90(5|9)\\d{7}")) {
            phoneIsCorrect = true;
            System.out.println("phoneIsCorrect = " + phoneIsCorrect);
        }
        if (nameIsCorrect && phoneIsCorrect) {
            PotentialOwner owner = new PotentialOwner(id, name, phone);
            potentialOwnerRepository.save(owner);
            return true;
        }
        return false;
    }

    public PotentialOwner get(Long id) {
        return potentialOwnerRepository.findById(id).orElse(null);
    }

}

