package uz.com.redis.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.com.redis.dto.PersonDto;
import uz.com.redis.dto.RangeDto;
import uz.com.redis.service.RedisListCache;
import uz.com.redis.service.RedisValueCache;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {
    private final RedisValueCache valueCache;
    private final RedisListCache listCache;

    @PostMapping
    public void cachePerson(@RequestBody final PersonDto personDto){
        valueCache.cache(personDto.getId(),personDto);
    }

    @GetMapping("/{id}")
    public PersonDto getPersonDto(@PathVariable final String id){
        return (PersonDto) valueCache.getCacheValue(id);
    }

    @DeleteMapping("/{id}")
    public void deletePersonDto(@PathVariable final String id){
        valueCache.deleteCacheValue(id);
    }

    @PostMapping("/list/{key}")
    public void cachePersons(@PathVariable final String key, @RequestBody List<PersonDto>personDtoList){
        listCache.cachePersons(key,personDtoList);
    }

    @GetMapping("/list/{key}")
    public List<PersonDto> getPersonsRange(@PathVariable final String key,@RequestBody final RangeDto rangeDto){
        return listCache.getPersonsRange(key,rangeDto);
    }

    @GetMapping("/list")
    public PersonDto getLastElement(final String key){
        return listCache.getLastElement(key);
    }

    @DeleteMapping("/list/{key}")
    public void trim(final String key,final RangeDto rangeDto){
        listCache.trim(key, rangeDto);
    }
}
