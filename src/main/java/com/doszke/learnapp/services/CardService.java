package com.doszke.learnapp.services;

import com.doszke.learnapp.data.DAOFactory;
import com.doszke.learnapp.data.DAOMapper;
import com.doszke.learnapp.data.dao.CardCategoryDAO;
import com.doszke.learnapp.data.dao.CardDAO;
import com.doszke.learnapp.repositories.CardCategoryRepository;
import com.doszke.learnapp.repositories.CardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private CardRepository cardRepository;
    private CardCategoryRepository cardCategoryRepository;
    private DAOFactory factory;
    private DAOMapper mapper;

    protected CardService(CardRepository repo, CardCategoryRepository cardCategoryRepository, DAOFactory factory, DAOMapper mapper) {
        this.cardRepository = repo;
        this.cardCategoryRepository = cardCategoryRepository;
        this.factory = factory;
        this.mapper = mapper;
    }

    protected String addCard(String[] data1, String[] data2){
        if(data1.length == 2) { //TODO unit test popraw
            Optional<CardDAO> searched = cardRepository.findByFrontAndBack(data1[0], data1[1]);
            if (!searched.isPresent()) {
                CardDAO card = factory.createCard(data1);
                CardCategoryDAO category = this.searchCategory(data2);
                if (category != null) {
                    card.setCategory(category);
                    category.add(card);
                    cardRepository.save(card);
                    return "Card added. ";
                }
                return "There is no such category. ";
            }
            return "There is already such a card. ";
        }
        return "Too much arguments passed. ";
    }

    private CardCategoryDAO searchCategory(String[] data){
        Optional<CardCategoryDAO> result = cardCategoryRepository.findByName(data[0]);
        return result.orElse(null);
    }

    protected List<String[]> getAllCards(){
        List<CardDAO> res = (List<CardDAO>) cardRepository.findAll();
        return mapper.mapList(res);
    }

    protected String removeCard(String[] data) {
        Optional<CardDAO> searched = cardRepository.findByFrontAndBack(data[0], data[1]);
        if (searched.isPresent()) {
            CardDAO card = searched.get();
            cardRepository.deleteById(card.getId());
            return "Card removed. ";
        }
        return "There is no card specified by given data. ";
    }

    protected List<String[]> getAllCategories(){
        List<CardCategoryDAO> result = (List<CardCategoryDAO>) cardCategoryRepository.findAll();
        return mapper.mapList(result);
    }

    protected List<String[]> getCardsByCategory(String name) {
        Optional<CardCategoryDAO> result = cardCategoryRepository.findByName(name);
        if (result.isPresent()) {
            CardCategoryDAO category = result.get();
            return mapper.mapList(category.getCards());
        }
        return new ArrayList<>();
    }

    protected String addCategory(String[] data){
        if(data.length == 1) {
            Optional<CardCategoryDAO> searched = cardCategoryRepository.findByName(data[0]);
            if (!searched.isPresent()) {
                CardCategoryDAO card = factory.createCardCategory(data);
                cardCategoryRepository.save(card);
                return "Card category added. ";
            }
            return "There is already such a category. ";
        }
        return "Too much arguments passed. ";
    }

    protected String removeCategory(String[] data, String... args) {
        Optional<CardCategoryDAO> searched = cardCategoryRepository.findByName(data[0]);
        if (searched.isPresent()) {
            CardCategoryDAO category = searched.get();
            if (category.getCards().isEmpty()) {
                cardCategoryRepository.deleteById(category.getId());
                return "Category removed. ";
            } else if (args.length > 0) {
                if (args[0].equals(CardCategoryDAO.REMOVE)) {
                    List<CardDAO> cards = category.getCards();
                    cards.forEach(e -> {
                        cardRepository.delete(e);
                    });
                    cardCategoryRepository.deleteById(category.getId());
                    return "Category removed. ";
                }
            }
            return "This category contains bound cards. Do you wish to remove this category nonetheless? ";
        }
        return "There is no category defined by given data. ";
    }

}
