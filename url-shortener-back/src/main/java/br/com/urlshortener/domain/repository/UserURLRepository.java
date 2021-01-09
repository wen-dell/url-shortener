package br.com.urlshortener.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.urlshortener.domain.model.UserURL;

@Repository
@Transactional("TransactionManager")
public interface UserURLRepository extends JpaRepository<UserURL, Long>, JpaSpecificationExecutor<UserURL> {

}
