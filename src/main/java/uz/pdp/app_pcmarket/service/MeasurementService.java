package uz.pdp.app_pcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app_pcmarket.entity.measurement.Measurement;
import uz.pdp.app_pcmarket.payload.ApiResponse;
import uz.pdp.app_pcmarket.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    @Autowired
    MeasurementRepository measurementRepository;

    public ApiResponse add(Measurement measurement) {
        boolean existsByName = measurementRepository.existsByName(measurement.getName());

        if (existsByName)
            return new ApiResponse("This measurement already added", false);

        measurementRepository.save(measurement);

        return new ApiResponse("The measurement added", true);
    }

    public List<Measurement> getAll() {
        List<Measurement> all = measurementRepository.findAll();

        return all;
    }

    public Measurement getById(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);

        if (optionalMeasurement.isPresent()) {
            return optionalMeasurement.get();
        }
        return new Measurement();
    }

    public ApiResponse edit(Measurement comingMeasurement, Integer id) {

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);

        if (optionalMeasurement.isEmpty())
            return new ApiResponse("The measurement not found", false);


        Measurement measurement = optionalMeasurement.get();
        measurement.setName(comingMeasurement.getName());

        measurementRepository.save(measurement);
        return new ApiResponse("The measurement edited", true);


    }

    public ApiResponse delete(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);

        if (optionalMeasurement.isEmpty())
            return new ApiResponse("The measurement not found", false);

        measurementRepository.deleteById(id);
        return new ApiResponse("The measurement deleted", true);
    }
}
