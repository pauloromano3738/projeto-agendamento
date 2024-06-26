package agendamentos.salas.agendamento.domain.cliente;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Cacheable("clientes")
    List<Cliente> findAll();

    @CacheEvict(value = "clientes", allEntries = true)
    <S extends Cliente> S save(S entity);

    @Override
    @CacheEvict(value = "clientes", allEntries = true)
    void deleteById(Integer integer);
}
