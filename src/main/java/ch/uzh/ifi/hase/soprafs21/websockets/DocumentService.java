package ch.uzh.ifi.hase.soprafs21.websockets;

import ch.uzh.ifi.hase.soprafs21.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
@Transactional
public class DocumentService {

    private final Logger log = LoggerFactory.getLogger(DocumentService.class);

    private final DocumentRepository documentRepository;


    @Autowired
    public DocumentService(@Qualifier("documentRepository") DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public SharedDocument getDocument(Long id) {
        if (this.documentRepository.findById(id).isEmpty()) {
            var newDoc = new SharedDocument();
            newDoc.setId(id);
            newDoc.setData("...");

            return documentRepository.saveAndFlush(newDoc);
        }
        else return this.documentRepository.findById(id).get();
    }

    public void updateDocument(SharedDocument document) {
        if (document.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The given id must not be null.");
        }
        var docToUpdate = getDocument(document.getId());
        if (document.getData() == null) return;

        docToUpdate.setData(document.getData());
        documentRepository.saveAndFlush(docToUpdate);
    }
}
