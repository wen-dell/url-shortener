package br.com.urlshortener.domain.repository;

import br.com.urlshortener.domain.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional("TransactionManager")
public interface TokenRepository extends JpaRepository<Token, Long> {
	
	Token findByUserId(Long userId);
	
}
