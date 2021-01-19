package tech.punklu.myspringboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.punklu.myspringboot.demo.model.Mood;

/**
 * 说说 Repository
 */
public interface MoodRepository extends JpaRepository<Mood, String> {


}
