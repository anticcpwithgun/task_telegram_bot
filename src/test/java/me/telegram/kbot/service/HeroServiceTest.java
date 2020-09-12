package me.telegram.kbot.service;

import me.telegram.kbot.constant.BotConstant;
import me.telegram.kbot.model.Hero;
import me.telegram.kbot.repository.HeroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @author freealways
 * @date 2020/7/30 21:03
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class HeroServiceTest {
    @Autowired
    private HeroService heroService;
    @Autowired
    private HeroRepository heroRepository;

    @Test
    public void save() {
        Hero hero = new Hero();
        hero.setHeroId("750111344");
        hero.setUserName("trent");
        hero.setName("trent");
        hero.setIntegral(0);
        hero.setLevel(0);
        hero.setTaskReceiveTimes(0);
        hero.setTaskActionTimes(0);
        hero.setActionSuccessTimes(0);
        hero.setActionFailTimes(0);
        hero.setCheckSuccessTimes(0);
        hero.setCheckFailTimes(0);
        heroService.save(hero);
    }

    @Test
    public void findById() {
        Hero hero = heroService.findById("123");
        System.out.println("hero = " + hero);
    }

    @Test
    public void findAll() {
        this.save();
        List<Hero> heroList = heroRepository.findAll();
        heroList.forEach(hero -> {
                    System.out.println("hero.toString() = " + hero.toString());
                    String text = String.format(BotConstant.integralInfo,
                    hero.getIntegral(),hero.getLevel(),
                    hero.getTaskReceiveTimes(),
                    hero.getTaskActionTimes(),
                    hero.getActionSuccessTimes(),
                    hero.getActionFailTimes());
                    System.out.println(text);
                }
        );
    }
}