package org.example.sae501serveur.Service;

import org.example.sae501serveur.Entity.Type;
import org.example.sae501serveur.Repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    public Type getTypeById(Long id) {
        return typeRepository.findById(id).orElse(null);
    }

    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

}
