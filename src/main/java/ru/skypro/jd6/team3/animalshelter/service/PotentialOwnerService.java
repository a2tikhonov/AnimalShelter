package ru.skypro.jd6.team3.animalshelter.service;

import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwner;
import ru.skypro.jd6.team3.animalshelter.repository.PotentialOwnerRepository;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PotentialOwnerService {

    private final PotentialOwnerRepository potentialOwnerRepository;

    public PotentialOwnerService(PotentialOwnerRepository potentialOwnerRepository) {
        this.potentialOwnerRepository = potentialOwnerRepository;
    }

    public void add(Long id) {
        PotentialOwner potentialOwner = new PotentialOwner();
        potentialOwner.setId(id);
        potentialOwner.setName("");
        potentialOwner.setPhone("");
        potentialOwnerRepository.save(potentialOwner);
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
        String phone = msg[1];
        if (!name.isBlank() && name.length() >= 2) {
            name = name.toLowerCase(Locale.ROOT);
            String firstLetter = name.substring(0, 1).toUpperCase(Locale.ROOT);
            name = firstLetter + name.substring(1);
            nameIsCorrect = true;
        }
        if (phone.matches("[0-9]+")
                && phone.charAt(0) == '+'
                && phone.charAt(1) == '7'
                && phone.length() == 12
                || phone.matches("[0-9]")
                && phone.charAt(0) == '8'
                && phone.length() == 11) {
            phoneIsCorrect = true;
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

