package com.behnam.university.service.implemention;

import com.behnam.university.dto.create.CollegeCreateDto;
import com.behnam.university.dto.detail.CollegeDetailDto;
import com.behnam.university.dto.list.CollegeListDto;
import com.behnam.university.dto.update.CollegeUpdateDto;
import com.behnam.university.mapper.CollegeMapper;
import com.behnam.university.mapper.static_mapper.StaticMapper;
import com.behnam.university.model.College;
import com.behnam.university.repository.CollegeRepository;
import com.behnam.university.service.interfaces.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Behnam Si
 */

@Service("collegeServiceImp")
@Primary
public class CollegeServiceImp implements CollegeService {

    private final CollegeRepository repository;

    @Autowired
    public CollegeServiceImp(CollegeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Deprecated
    public List<CollegeCreateDto> getAllColleges(Integer page, Integer limit) {
        CollegeMapper mapper = new CollegeMapper();
        // page and limit filters
        if (limit == null) limit = 3;
        if (page == null) page = 0;
        else page -= 1;
        if (limit > 100) throw new IllegalStateException("limit can not be more than 100");
        // paging and sorting and getting the data
        Pageable collegePageable = PageRequest.of(page, limit, Sort.by("collegeName").ascending());
        Page<College> collegePage = repository.findAll(collegePageable);
        // turn them into college dto
        List<CollegeCreateDto> resultColleges = new ArrayList<CollegeCreateDto>();
        for (College college :
                collegePage.getContent()) {
            CollegeCreateDto collegeCreateDto = new CollegeCreateDto();
            collegeCreateDto = mapper.toCollegeDto(college);
            resultColleges.add(collegeCreateDto);
        }
        // return the result
        return resultColleges;
    }

    @Override
    public List<CollegeListDto> getAllColleges(Pageable pageable) {
        List<College> colleges = repository.findAll(pageable).getContent();
        List<CollegeListDto> collegeDetailDTOS = new ArrayList<>();
        for (College c :
                colleges) {
            CollegeListDto dto = new CollegeListDto();
            try {
                StaticMapper.mapper(c, dto);
                collegeDetailDTOS.add(dto);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return collegeDetailDTOS;
    }

    @Override
    @Transactional
    public CollegeDetailDto getCollege(String collegeName) {
        if (!repository.existsCollegeByCollegeName(collegeName))
            throw new IllegalStateException("invalid college name");
        College college = repository.findCollegeByCollegeName(collegeName);
        CollegeDetailDto dto = new CollegeDetailDto();
        try {
            StaticMapper.mapper(college, dto);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return dto;
    }

    @Override
    public CollegeCreateDto addCollege(CollegeCreateDto collegeCreateDto) {
        // turn into college
        CollegeMapper mapper = new CollegeMapper();
        College college = mapper.toCollege(collegeCreateDto);
        // saving to database
        repository.save(college);
        return collegeCreateDto;
    }

    @Override
    @Transactional
    public String deleteCollege(String collegeName) {
        if (!repository.existsCollegeByCollegeName(collegeName)) {
            throw new IllegalStateException("this college does not exists to delete.");
        }
        repository.deleteCollegeByCollegeName(collegeName);
        return collegeName;
    }

    @Override
    @Transactional
    public void updateCollege(Long collegeId, String collegeName) {
        if (!repository.existsById(collegeId)) throw new IllegalStateException("invalid college id");
        College college = repository.findById(collegeId).get();
        if (collegeName != null && collegeName.length() > 0
                && !Objects.equals(collegeName, college.getCollegeName())) {
            college.setCollegeName(collegeName);
        }
    }

    @Override
    @Transactional
    public CollegeUpdateDto updateCollege(Long collegeId, CollegeUpdateDto dto) {
        College college = repository.findById(collegeId).orElseThrow(()->
                new IllegalStateException("invalid college id"));
        if (dto.getCollegeName()!= null){
            college.setCollegeName(dto.getCollegeName());
        }
        return dto;
    }

}
