package me.telegram.kbot.service.impl;

import me.telegram.kbot.model.Hero;
import me.telegram.kbot.repository.HeroRepository;
import me.telegram.kbot.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author freealways
 * @date 2020/7/30 21:02
 */
@Service
public class HeroServiceImpl implements HeroService {
    @Autowired
    private HeroRepository heroRepository;
    @Override
    public void save(Hero hero) {
        heroRepository.save(hero);
    }

    @Override
    public Hero findById(String heroId) {
        return heroRepository.findByHeroId(heroId);
    }
}
