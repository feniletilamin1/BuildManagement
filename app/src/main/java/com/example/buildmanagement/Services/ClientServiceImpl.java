package com.example.buildmanagement.Services;

import com.example.buildmanagement.Models.Client;
import com.example.buildmanagement.Repositories.ClientRepositoryImpl;

import java.util.List;

/**
 * Реализация интерфейса ClientService для работы с клиентами.
 */
public class ClientServiceImpl implements ClientService {
    ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();

    /**
     * Возвращает список всех клиентов.
     *
     * @return список клиентов
     */
    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    /**
     * Возвращает клиента по указанному идентификатору.
     *
     * @param id идентификатор клиента
     * @return клиент
     */
    @Override
    public Client findOneById(int id) {
        return clientRepository.findOneById(id);
    }

    /**
     * Создает нового клиента.
     *
     * @param client клиент для создания
     */
    @Override
    public int create(Client client) {
        int newId = clientRepository.save(client);
        return newId;
    }

    /**
     * Обновляет данные клиента.
     *
     * @param client клиент для обновления
     */
    @Override
    public void update(Client client) {
        clientRepository.update(client);
    }

    /**
     * Удаляет клиента по указанному идентификатору.
     *
     * @param id идентификатор клиента для удаления
     */
    @Override
    public void delete(int id) {
        clientRepository.delete(id);
    }
}
