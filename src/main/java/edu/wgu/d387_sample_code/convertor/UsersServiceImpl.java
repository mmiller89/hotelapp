package edu.wgu.d387_sample_code.convertor;

import edu.wgu.d387_sample_code.entity.RoomEntity;
import edu.wgu.d387_sample_code.entity.UsersEntity;
import edu.wgu.d387_sample_code.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Project: D387 sample code
 * Package: edu.wgu.d387_sample_code.convertor
 * <p>
 * User: carolyn.sher
 * Date: 9/16/2022
 * Time: 4:54 PM
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UsersServiceImpl implements UsersService{
    private UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository){
        this.usersRepository=usersRepository;

    }


    public void saveUser(UsersEntity usersEntity) {
        usersRepository.save(usersEntity);
    }

    public UsersEntity findUserById(long theId) {

    Optional<UsersEntity> result = usersRepository.findById(theId);

    UsersEntity theUser = null;

    if (result.isPresent()) {
        theUser = result.get();
    }
    else {
        // we didn't find the employee
        //throw new RuntimeException("Did not find part id - " + theId);
        return null;
    }

    return theUser;

    }



}
