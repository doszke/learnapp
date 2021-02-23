package com.doszke.learnapp.services;

import com.doszke.learnapp.data.dao.CardCategoryDAO;
import com.doszke.learnapp.data.dao.CardDAO;
import com.doszke.learnapp.repositories.CardCategoryRepository;
import com.doszke.learnapp.repositories.CardRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CardServiceTest {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardCategoryRepository cardCategoryRepository;

    @Test
    @Order(1)
    void addCategoryTest() {
        int counter = 0;
        for (String[] row : Data.CATEGORY_REPO_DATA) {
            CardCategoryDAO expected = new CardCategoryDAO();
            expected.setName(row[0]);
            String result = cardService.addCategory(row);
            assertEquals(Data.CATEGORY_ADDED, result);
            List<CardCategoryDAO> res = (List<CardCategoryDAO>) cardCategoryRepository.findAll();
            assertEquals(++counter, res.size());
            CardCategoryDAO actual = res.get(res.size() - 1);
            assertEquals(expected, actual);
        }
    }

    @Test
    @Order(2)
    void addDuplicateCategory() {
        List<CardCategoryDAO> expected = ((List<CardCategoryDAO>) cardCategoryRepository.findAll());
        for (String[] row : Data.CATEGORY_REPO_DATA) {
            CardCategoryDAO expectedDao = new CardCategoryDAO();
            expectedDao.setName(row[0]);
            String result = cardService.addCategory(row);
            assertEquals(Data.CATEGORY_DUPLICATE, result);
            List<CardCategoryDAO> res = (List<CardCategoryDAO>) cardCategoryRepository.findAll();
            assertEquals(expected, res);
            assertTrue(res.contains(expectedDao));
        }
    }

    @Test
    @Order(3)
    void categoryInvalidArgLen() {
        List<CardCategoryDAO> expected = (List<CardCategoryDAO>) cardCategoryRepository.findAll();
        for (String[] row : Data.CATEGORY_REPO_DATA_INVALID) {
            String result = cardService.addCategory(row);
            assertEquals(Data.INVALID_ARG_LEN, result);
            List<CardCategoryDAO> res = (List<CardCategoryDAO>) cardCategoryRepository.findAll();
            assertEquals(expected, res);
        }
    }

    @Test
    @Order(4)
    void getAllCategories() {
        List<String[]> result = cardService.getAllCategories();
        String[][] expectedData = Data.CATEGORY_REPO_DATA;
        for (int i = 0; i < expectedData.length; i++) {
            assertArrayEquals(expectedData[i], result.get(i));
        }
    }

    @Test
    @Order(5)
    void addCardTest() {
        int counter = 0;
        for (int i = 0; i < Data.CARD_REPO_DATA.length; i++) {
            String[] data1 = Data.CARD_REPO_DATA[i];
            String[] data2 = Data.CATEGORY_REPO_DATA[i];
            CardDAO expected = new CardDAO();
            expected.setFront(data1[0]);
            expected.setBack(data1[1]);
            CardCategoryDAO category = cardCategoryRepository.findByName(data2[0]).orElse(null);
            expected.setCategory(category);
            String result = cardService.addCard(data1, data2);
            assertEquals(Data.CARD_ADDED, result);
            List<CardDAO> res = (List<CardDAO>) cardRepository.findAll();
            assertEquals(++counter, res.size());
            assertEquals(expected, res.get(res.size() - 1));
        }
    }

    @Test
    @Order(6)
    void addDuplicateCards() {
        List<CardDAO> expected = (List<CardDAO>) cardRepository.findAll();
        for (int i = 0; i < Data.CARD_REPO_DATA.length; i++) {
            String[] data1 = Data.CARD_REPO_DATA[i];
            String[] data2 = Data.CATEGORY_REPO_DATA[i];
            CardDAO expectedDao = new CardDAO();
            expectedDao.setFront(data1[0]);
            expectedDao.setBack(data1[1]);
            CardCategoryDAO category = cardCategoryRepository.findByName(data2[0]).orElse(null);
            expectedDao.setCategory(category);
            String result = cardService.addCard(data1, data2);
            assertEquals(Data.CARD_DUPLICATE, result);
            List<CardDAO> res = (List<CardDAO>) cardRepository.findAll();
            assertEquals(expected, res);
            assertTrue(res.contains(expectedDao));
        }
    }

    @Test
    @Order(7)
    void addCardInvalidArgLen() {
        List<CardDAO> expected = (List<CardDAO>) cardRepository.findAll();
        for (int i = 0; i < Data.CARD_REPO_DATA.length; i++) {
            String[] data1 = Data.CARD_REPO_DATA_INVALID[i];
            String[] data2 = Data.CATEGORY_REPO_DATA_INVALID[i];
            String result = cardService.addCard(data1, data2);
            assertEquals(Data.INVALID_ARG_LEN, result);
            List<CardDAO> res = (List<CardDAO>) cardRepository.findAll();
            assertEquals(expected, res);
        }
    }


    @Test
    @Order(8)
    void findAll() {
        List<String[]> items = cardService.getAllCards();
        List<CardDAO> result = items.stream().map(e -> {
            CardDAO c = new CardDAO();
            c.setFront(e[0]);
            c.setBack(e[1]);
            CardCategoryDAO cat = cardCategoryRepository.findByName(e[2]).orElse(null);
            c.setCategory(cat);
            return c;
        }).collect(Collectors.toList());
        List<CardDAO> expected = (List<CardDAO>) cardRepository.findAll();
        assertEquals(expected, result);
    }

    @Test
    @Order(9)
    void getAllCardsByCategory() throws Exception {
        for (String[] data : Data.CATEGORY_REPO_DATA) {
            int len = cardCategoryRepository.findByName(data[0]).orElseThrow(Exception::new).getCards().size();
            List<String[]> temp = cardService.getCardsByCategory(data[0]);
            assertEquals(len, temp.size());
            for (String[] d : temp) {
                assertEquals(d[2], data[0]);
            }
        }
    }

    @Test
    @Order(10)
    void removeCardTest() {
        List<CardDAO> elements = ((List<CardDAO>) cardRepository.findAll());
        int len = elements.size();
        String expected = Data.CARD_REMOVED;
        for (String[] data : Data.CARD_REPO_DATA) {
            Optional<CardDAO> search = cardRepository.findByFrontAndBack(data[0], data[1]);
            if (search.isPresent()) {
                CardDAO obj = search.get();
                CardCategoryDAO categoryDAO = obj.getCategory();
                String result = cardService.removeCard(data);
                assertEquals(expected, result);
                int newLen = ((List<CardDAO>) cardRepository.findAll()).size();
                assertEquals(--len, newLen);
                CardCategoryDAO resultt = cardCategoryRepository.findByName(categoryDAO.getName()).orElse(null);
                if (resultt == null) throw new NullPointerException();
                assertFalse(resultt.getCards().contains(obj));
            }
        }
    }

    @Test
    @Order(11)
    void removeCardsAbsentInDb() {
        List<CardDAO> elements = ((List<CardDAO>) cardRepository.findAll());
        int len = elements.size();
        String expected = Data.CARD_NOT_PRESENT;
        for (String[] data : Data.CARD_REPO_DATA) {
            String result = cardService.removeCard(data);
            assertEquals(expected, result);
            int newLen = ((List<CardDAO>) cardRepository.findAll()).size();
            assertEquals(len, newLen);
        }
    }

    @Test
    @Order(12)
    void removeCategoryWithCardsAsk(){
        for (int i = 0; i <  Data.CATEGORY_REPO_DATA.length; i++) {
            List<CardDAO> before = (List<CardDAO>) cardRepository.findAll();
            cardService.addCard(Data.CARD_REPO_DATA[i], Data.CATEGORY_REPO_DATA[i]);
            List<CardDAO> after = (List<CardDAO>) cardRepository.findAll();
            assertEquals(before.size() + 1, after.size());
        }
        for(String[] data: Data.CATEGORY_REPO_DATA) {
            String result = cardService.removeCategory(data);
            assertEquals(Data.CATEGORY_HAS_CARDS, result);
            assertTrue(cardCategoryRepository.findByName(data[0]).isPresent());
        }
    }

    @Test
    @Order(13)
    void removeCategoryWithCardWithoutAsking(){
        List<CardCategoryDAO> categories = (List<CardCategoryDAO>) cardCategoryRepository.findAll();
        for(String[] data: Data.CATEGORY_REPO_DATA) {
            String result = cardService.removeCategory(data, CardCategoryDAO.REMOVE);
            assertEquals(Data.CATEGORY_REMOVED, result);
            assertFalse(cardCategoryRepository.findByName(data[0]).isPresent());
            List<String[]> cards = cardService.getCardsByCategory(data[0]);
            assertTrue(cards.isEmpty());
            CardCategoryDAO category = (CardCategoryDAO) (categories.stream().filter(e -> e.getName().equals(data[0])).toArray()[0]);
            List<CardDAO> allCards = (List<CardDAO>) cardRepository.findAll();
            allCards.forEach(e -> assertNotEquals(e.getCategory(), category));
        }
    }

    @Test
    @Order(14)
    void removeCategoryWithNoCards(){
        for (int i = 0; i <  Data.CATEGORY_REPO_DATA.length; i++) {
            List<CardCategoryDAO> before = (List<CardCategoryDAO>) cardCategoryRepository.findAll();
            cardService.addCategory(Data.CATEGORY_REPO_DATA[i]);
            List<CardCategoryDAO> after = (List<CardCategoryDAO>) cardCategoryRepository.findAll();
            assertEquals(before.size() + 1, after.size());
        }
        for (String[] data: Data.CATEGORY_REPO_DATA) {
            List<CardCategoryDAO> before = (List<CardCategoryDAO>) cardCategoryRepository.findAll();
            String result = cardService.removeCategory(data);
            assertEquals(Data.CATEGORY_REMOVED, result);
            List<CardCategoryDAO> after = (List<CardCategoryDAO>) cardCategoryRepository.findAll();
            CardCategoryDAO dummy = new CardCategoryDAO();
            dummy.setName(data[0]);
            assertFalse(after.contains(dummy));
            assertEquals(before.size() - 1, after.size());

        }
    }

    @Test
    @Order(15)
    void removeCategoryNotPresent(){
        List<CardCategoryDAO> before = (List<CardCategoryDAO>) cardCategoryRepository.findAll();
        for (String[] data: Data.CATEGORY_REPO_DATA) {
            String result = cardService.removeCategory(data);
            assertEquals(Data.CATEGORY_NOT_FOUND, result);
            List<CardCategoryDAO> after = (List<CardCategoryDAO>) cardCategoryRepository.findAll();
            assertEquals(before, after);
        }
    }
}
