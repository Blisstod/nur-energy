package kz.nur.energy.service;

import kz.nur.energy.dto.ControlPointResponse;
import kz.nur.energy.entity.ControlPoint;
import kz.nur.energy.repository.ControlPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ControlPointService {

    @Autowired
    private ControlPointRepository controlPointRepository;

    @Transactional(readOnly = true)
    public List<ControlPointResponse> getAll() {
        List<ControlPoint> points = controlPointRepository.findAll();
        return points.stream().map(ControlPointResponse::of).toList();
    }

    @Transactional(readOnly = true)
    public ControlPointResponse getById(UUID id) {
        ControlPoint point = controlPointRepository.getById(id);
        return ControlPointResponse.of(point);
    }
}
