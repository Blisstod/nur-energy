package kz.nur.energy.service;

import jakarta.persistence.EntityNotFoundException;
import kz.nur.energy.dto.AutoItem;
import kz.nur.energy.dto.BrandItem;
import kz.nur.energy.dto.ModelItem;
import kz.nur.energy.entity.AutoBrand;
import kz.nur.energy.entity.AutoModel;
import kz.nur.energy.entity.Auto;
import kz.nur.energy.entity.User;
import kz.nur.energy.repository.AutoBrandRepository;
import kz.nur.energy.repository.AutoModelRepository;
import kz.nur.energy.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutoService {

    @Autowired
    AutoRepository autoRepository;
    @Autowired
    AutoBrandRepository autoBrandRepository;
    @Autowired
    AutoModelRepository autoModelRepository;

    @Transactional
    public AutoItem addAuto(User user, final AutoItem request) {
        Auto auto = new Auto();

        AutoModel autoModel = autoModelRepository.findById(request.getModel().getId())
                .orElseThrow(() -> new EntityNotFoundException("Auto Model not found"));

        auto.setUser(user);
        auto.setModel(autoModel);
        auto.setNum(request.getNum());
        auto.setIsActive(true);
        autoRepository.save(auto);

        return AutoItem.of(auto);
    }

    @Transactional
    public ModelItem addAutoModel(User user, final ModelItem request) {
        AutoModel model = new AutoModel();

        AutoBrand autoBrand = autoBrandRepository.findById(request.getBrand().getId())
                .orElseThrow(() -> new EntityNotFoundException("Auto Brand not found"));

        model.setBrand(autoBrand);
        model.setName(request.getName());
        autoModelRepository.save(model);

        return ModelItem.of(model);
    }

    @Transactional
    public BrandItem addAutoBrand(User user, final BrandItem request) {
        AutoBrand autoBrand = new AutoBrand();

        autoBrand.setName(request.getName());
        autoBrandRepository.save(autoBrand);

        return BrandItem.of(autoBrand);
    }

    @Transactional(readOnly = true)
    public List<AutoItem> getAutoList(User user) {
        return AutoItem.listOf(autoRepository.findByUser(user));
    }
}
