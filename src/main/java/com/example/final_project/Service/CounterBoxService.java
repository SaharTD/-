package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.CounterBox;
import com.example.final_project.Repository.CounterBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CounterBoxService {

    private final CounterBoxRepository counterBoxRepository;

    public void addCounterBox(CounterBox counterBox) {
        counterBoxRepository.save(counterBox);
    }

    public List getAllCounterBoxes() {
        return counterBoxRepository.findAll();
    }

    public CounterBox getCounterBox(Integer id) {
        CounterBox counterBox = counterBoxRepository.findCounterBoxById(id);
        if (counterBox == null) {
            throw new ApiException("counterBox not found");
        }
        return counterBox;
    }

    public void updateCounterBox(CounterBox counterBox, Integer id) {
        CounterBox oldBox = counterBoxRepository.findCounterBoxById(id);
        if (oldBox == null) {
            throw new ApiException("counterBox not found");
        }

        oldBox.setType(counterBox.getType());
        oldBox.setPaymentType(counterBox.getPaymentType());
        oldBox.setDailyTreasury(counterBox.getDailyTreasury());
        oldBox.setOpenDatetime(counterBox.getOpenDatetime());
        oldBox.setCloseDatetime(counterBox.getCloseDatetime());

        counterBoxRepository.save(oldBox);
    }

    public void deleteCounterBox(Integer id) {
        CounterBox box = counterBoxRepository.findCounterBoxById(id);
        if (box == null) {
            throw new ApiException("counterBox not found");
        }
        counterBoxRepository.delete(box);
    }
}
