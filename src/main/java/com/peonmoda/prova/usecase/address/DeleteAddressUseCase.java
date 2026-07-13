package com.peonmoda.prova.usecase.address;

import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peonmoda.prova.entity.AddressEntity;
import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.exception.AddressNotRelatedToPersonException;
import com.peonmoda.prova.service.AddressService;
import com.peonmoda.prova.service.PersonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteAddressUseCase {

    private final PersonService personService;
    private final AddressService addressService;

    public void execute(UUID personId,
            UUID addressId) throws NotFoundException {

        PersonEntity person = personService.searchById(personId);

        AddressEntity address = addressService.searchById(addressId);
        
        if (!address.getPessoa().getId().equals(person.getId())) {
            throw new AddressNotRelatedToPersonException();
        }

        long ativos = person.getEnderecos()
                .stream()
                .filter(AddressEntity::getAtivo)
                .count();

        if (ativos < 1) {
            throw new AddressNotRelatedToPersonException();
        }

        address.setAtivo(false);

        addressService.save(address);
    }

}