package br.com.pulsar.products.infra.configs;

import br.com.pulsar.products.infra.persistence.repositories.SpringDataProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleUpdate {

    @Autowired
    private SpringDataProductRepository springDataProductRepository;

    @Transactional
    @Scheduled(cron = "0 0 6 * * *")
    public void updateInactiveProductSchedule(){
//        springDataProductRepository.updateInactiveProducts();
    }
}
