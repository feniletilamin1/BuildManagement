package com.example.buildmanagement.Services;

import com.example.buildmanagement.Models.BuildObject;
import com.example.buildmanagement.Models.ObjectPhoto;
import com.example.buildmanagement.Repositories.BuildObjectRepositoryImpl;

import java.util.List;

/**
 * Реализация интерфейса BuildObjectService для работы с объектами строительства.
 */
public class BuildObjectServiceImpl implements BuildObjectService {
    final BuildObjectRepositoryImpl buildObjectRepository = new BuildObjectRepositoryImpl();

    /**
     * Возвращает список всех объектов строительства.
     *
     * @return список объектов строительства
     */
    @Override
    public List<BuildObject> findAll() {
        return buildObjectRepository.findAll();
    }

    /**
     * Возвращает объект строительства по указанному идентификатору.
     *
     * @param id идентификатор объекта строительства
     * @return объект строительства
     */
    @Override
    public BuildObject findOneById(int id) {
        return buildObjectRepository.findOneById(id);
    }

    /**
     * Создает новый объект строительства.
     *
     * @param buildObject объект строительства для создания
     */
    @Override
    public int create(BuildObject buildObject) {
        int newId = buildObjectRepository.save(buildObject);
        return newId;
    }

    /**
     * Обновляет данные объекта строительства.
     *
     * @param buildObject объект строительства для обновления
     */
    @Override
    public void update(BuildObject buildObject) {
        buildObjectRepository.update(buildObject);
    }

    /**
     * Удаляет объект строительства по указанному идентификатору.
     *
     * @param id идентификатор объекта строительства для удаления
     */
    @Override
    public void delete(int id) {
        buildObjectRepository.delete(id);
    }

    /**
     * Добавляет сотрудника на объект строительства.
     *
     * @param objectId идентификатор объекта строительства
     * @param empId    идентификатор сотрудника
     */
    @Override
    public void addEmployeeOnObject(int objectId, int empId) {
        buildObjectRepository.addEmployeeOnObject(objectId, empId);
    }

    /**
     * Удаляет сотрудника с объекта строительства.
     *
     * @param empId идентификатор сотрудника
     */
    @Override
    public void removeEmployeeFromObject(int empId) {
        buildObjectRepository.removeEmployeeFromObject(empId);
    }

    @Override
    public long addObjectPhoto(ObjectPhoto objectPhoto) {
        long id = buildObjectRepository.addObjectPhoto(objectPhoto);
        return id;
    }

    @Override
    public List<ObjectPhoto> getObjectPhotos(int objectId) {
        return buildObjectRepository.getObjectPhotos(objectId);
    }

    @Override
    public void deletePhoto(int photoId) {
        buildObjectRepository.deletePhoto(photoId);
    }

    @Override
    public void finishWork(int objectId) {
        buildObjectRepository.finishWork(objectId);
    }
}
