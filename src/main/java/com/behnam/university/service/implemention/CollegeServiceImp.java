package com.behnam.university.service.implemention;

import com.behnam.university.dto.CollegeDto;
import com.behnam.university.mapper.CollegeMapper;
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
    public List<CollegeDto> getAllColleges(Integer page, Integer limit) {
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
        List<CollegeDto> resultColleges = new ArrayList<CollegeDto>();
        for (College college :
                collegePage.getContent()) {
            CollegeDto collegeDto = new CollegeDto();
            collegeDto = mapper.toCollegeDto(college);
            resultColleges.add(collegeDto);
        }
        // return the result
        return resultColleges;
    }

    @Override
    public College addCollege(CollegeDto collegeDto) {
        // turn into college
        CollegeMapper mapper = new CollegeMapper();
        College college = mapper.toCollege(collegeDto);
        // saving to database
        return repository.save(college);
    }

    @Override
    @Transactional
    public void deleteCollege(String collegeName) {
        if (!repository.existsCollegeByCollegeName(collegeName)) {
            throw new IllegalStateException("this college does not exists to delete.");
        }
        repository.deleteCollegeByCollegeName(collegeName);
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

}
