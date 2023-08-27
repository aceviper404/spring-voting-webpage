package com.demo.votingappwebpage.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.votingappwebpage.model.Vote;
import com.demo.votingappwebpage.repository.VotesRepository;
import com.opencsv.CSVWriter;

@Controller
public class WebController {

	@Autowired
	private VotesRepository votesRepository;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/getCandidates")
	public @ResponseBody List<Vote> getUsers() {
		return votesRepository.findAll();
	}

	@GetMapping("/downloadCSV")
	public ResponseEntity<ByteArrayResource> downloadCSV() {
		List<Vote> candidates = votesRepository.findAll();
		byte[] csvBytes = generateCSV(candidates); // Implement this method to generate CSV bytes

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=candidates.csv");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/csv"))
				.body(new ByteArrayResource(csvBytes));
	}

	private byte[] generateCSV(List<Vote> candidates) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try (CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream))) {
			String[] header = { "Name", "Number of Votes" };
			csvWriter.writeNext(header);

			for (Vote candidate : candidates) {
				String[] row = { candidate.getName(), String.valueOf(candidate.getNumberOfVotes()) };
				csvWriter.writeNext(row);
			}

			csvWriter.flush();
		} catch (IOException e) {
			// Handle exception appropriately
			e.printStackTrace();
		}

		return outputStream.toByteArray();
	}

}
