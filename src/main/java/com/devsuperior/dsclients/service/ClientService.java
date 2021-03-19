package com.devsuperior.dsclients.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsclients.dto.ClientDTO;
import com.devsuperior.dsclients.entities.Client;
import com.devsuperior.dsclients.repository.ClientRepository;
import com.devsuperior.dsclients.service.exception.ClientDataBaseExistsException;
import com.devsuperior.dsclients.service.exception.DataBaseException;
import com.devsuperior.dsclients.service.exception.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		return clientRepository.findAll(pageRequest).map(ClientDTO::new);
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO save(ClientDTO dto) {
		try {
			Client client = new Client();
			copyDtoToEntity(dto, client);
			return new ClientDTO(clientRepository.save(client));
		} catch (DataIntegrityViolationException e) {
			throw new ClientDataBaseExistsException("Cliente já cadastrado na base CPF: "+dto.getCpf());
		}
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client client = clientRepository.getOne(id);
			copyDtoToEntity(dto, client);
			return new ClientDTO(clientRepository.save(client));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado: "+id);
		} catch (DataIntegrityViolationException e) {
			throw new ClientDataBaseExistsException("Já eixiste cliente cadastrado na base com o CPF: "+dto.getCpf());
		}
	}

	public void delete(Long id) {
		try {
			clientRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integridade violada: "+id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado: "+id);
		}
	}

	private void copyDtoToEntity(ClientDTO dto, Client client) {
		client.setName(dto.getName());
		client.setCpf(dto.getCpf());
		client.setIncome(dto.getIncome());
		client.setBirthDate(dto.getBirthDate());
		client.setChildren(dto.getChildren());
	}
}
