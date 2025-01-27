package com.framezip.management.application.core.repository;

import com.framezip.management.application.core.repository.document.VideoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoFrameRepository extends MongoRepository<VideoDocument, String> {

    List<VideoDocument> findByUserIdAndCorrelationId(String userId, String correlationId);

    Optional<VideoDocument> findByUserIdAndId(String id, String userId);
}
