package local.example.welcome.data.service;

import local.example.welcome.data.entity.Address;
import local.example.welcome.data.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends CrudService<Address, Integer> {

    private AddressRepository addressRepository;

    public AddressService(@Autowired AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    protected AddressRepository abstractJpaRepository() {
        return addressRepository;
    }
}
