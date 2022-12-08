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

    @Override
    public String toString() {
        return "PotentialOwnerService";
    }

    public void save(PotentialOwner potentialOwner) {
        potentialOwnerRepository.save(potentialOwner);
    }

    public void add(Long id, String locationInMenu) {
        PotentialOwner potentialOwner = new PotentialOwner();
        potentialOwner.setId(id);
        potentialOwner.setName("");
        potentialOwner.setPhone(id.toString());
        potentialOwner.setLocationInMenu(locationInMenu);
        potentialOwnerRepository.save(potentialOwner);
    }

    public boolean find(Long id) {
        return potentialOwnerRepository.findById(id).isPresent();
    }

    public boolean addContactData(Long id, String message, String locationInMenu) {
        boolean nameIsCorrect = false;
        boolean phoneIsCorrect = false;
        String[] msg = message.split(" ");
        String name = msg[0];
        String phone = msg[1];
        if (!name.isBlank() && name.length() >= 2) {
            name = name.toLowerCase(Locale.ROOT);
            String firstLetter = name.substring(0, 1).toUpperCase(Locale.ROOT);
            name = firstLetter + name.substring(1);
            nameIsCorrect = true;
        }
        String phone1 = phone.replaceAll("\\D+", "");
        if (phone1.matches("(7|8)?9\\d{9}")) {
            phoneIsCorrect = true;
        }
        if (nameIsCorrect && phoneIsCorrect) {
            PotentialOwner owner = new PotentialOwner(id, name, phone1, locationInMenu);
            potentialOwnerRepository.save(owner);
            return true;
        }
        return false;
    }

    public PotentialOwner get(Long id) {
        return potentialOwnerRepository.findById(id).orElse(null);
    }

    public void setLocationInMenu(Long id, String button) {
        PotentialOwner potentialOwner = get(id);
        potentialOwner.setLocationInMenu(button);
        save(potentialOwner);
    }

    public String getLocationInMenu(Long id) {
        return get(id).getLocationInMenu();
    }

    public void delete(Long id) {
        potentialOwnerRepository.deleteById(id);
    }

}

