package me.telegram.kbot.service;

import me.telegram.kbot.model.Hero;

/**
 * @author freealways
 * @date 2020/7/30 21:00
 */

public interface HeroService {
   void save(Hero hero);
   Hero findById(String heroId);
}
