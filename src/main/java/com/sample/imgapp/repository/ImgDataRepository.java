package com.sample.imgapp.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.sample.imgapp.model.ImgData;

import java.util.List;

@Repository
public interface ImgDataRepository extends JpaRepository<ImgData, Long> {

    @Query(value="select * from saved_data where formatdatetime(date, 'dd/MM') = ?1 and year(date) > ?2", nativeQuery=true)
    List<ImgData> findAllByMemoryDate(String date, String year);
}
