package com.techcrunch.bluepay.service;

import com.techcrunch.bluepay.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;


@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(final ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceDTO> findAll() {
        final List<Service> services = serviceRepository.findAll(Sort.by("id"));
        return services.stream()
                .map(service -> mapToDTO(service, new ServiceDTO()))
                .toList();
    }

    public ServiceDTO get(final Long id) {
        return serviceRepository.findById(id)
                .map(service -> mapToDTO(service, new ServiceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ServiceDTO serviceDTO) {
        final Service service = new Service();
        mapToEntity(serviceDTO, service);
        return serviceRepository.save(service).getId();
    }

    public void update(final Long id, final ServiceDTO serviceDTO) {
        final Service service = serviceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(serviceDTO, service);
        serviceRepository.save(service);
    }

    public void delete(final Long id) {
        serviceRepository.deleteById(id);
    }

    private ServiceDTO mapToDTO(final Service service, final ServiceDTO serviceDTO) {
        serviceDTO.setId(service.getId());
        serviceDTO.setCode(service.getCode());
        serviceDTO.setName(service.getName());
        return serviceDTO;
    }

    private Service mapToEntity(final ServiceDTO serviceDTO, final Service service) {
        service.setCode(serviceDTO.getCode());
        service.setName(serviceDTO.getName());
        return service;
    }

    public boolean codeExists(final String code) {
        return serviceRepository.existsByCodeIgnoreCase(code);
    }

}
