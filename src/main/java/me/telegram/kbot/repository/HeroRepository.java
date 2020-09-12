package me.telegram.kbot.repository;

import me.telegram.kbot.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author freealways
 * @date 2020/7/30 20:50
 */
@Repository
public interface HeroRepository extends JpaRepository<Hero,String>,PagingAndSortingRepository<Hero,String> {
    Hero findByHeroId(String heroId);
}
