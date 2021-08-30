package uz.pdp.app_pcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app_pcmarket.entity.commentary.Commentary;
import uz.pdp.app_pcmarket.entity.user.User;
import uz.pdp.app_pcmarket.payload.ApiResponse;
import uz.pdp.app_pcmarket.payload.CommentaryDto;
import uz.pdp.app_pcmarket.repository.CommentaryRepository;
import uz.pdp.app_pcmarket.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentaryService {
    @Autowired
    CommentaryRepository commentaryRepository;
    @Autowired
    UserRepository userRepository;

    public ApiResponse add(CommentaryDto commentaryDto) {

        Commentary commentary = new Commentary();
        Optional<User> optionalUser = userRepository.findById(commentaryDto.getUserId());
        if (optionalUser.isEmpty())
            return new ApiResponse("User not found", false);

        commentary.setTitle(commentaryDto.getTitle());
        commentary.setText(commentaryDto.getText());
        commentary.setUser(optionalUser.get());

        commentaryRepository.save(commentary);

        return new ApiResponse("The commentary added", true);
    }

    public List<Commentary> getAll() {
        List<Commentary> all = commentaryRepository.findAll();

        return all;
    }

    public Commentary getById(Integer id) {
        Optional<Commentary> optionalCommentary = commentaryRepository.findById(id);

        return optionalCommentary.orElse(null);
    }

    public ApiResponse edit(CommentaryDto commentaryDto, Integer id) {

        Optional<Commentary> optionalCommentary = commentaryRepository.findById(id);

        if (optionalCommentary.isEmpty())
            return new ApiResponse("The commentary not found", false);

        Commentary commentary = optionalCommentary.get();
        Optional<User> optionalUser = userRepository.findById(commentaryDto.getUserId());
        if (optionalUser.isEmpty())
            return new ApiResponse("User not found", false);

        commentary.setTitle(commentaryDto.getTitle());
        commentary.setText(commentaryDto.getText());
        commentary.setUser(optionalUser.get());

        commentaryRepository.save(commentary);

        return new ApiResponse("The commentary edited", true);
    }

    public ApiResponse delete(Integer id) {

        Optional<Commentary> optionalCompany = commentaryRepository.findById(id);

        if (optionalCompany.isEmpty())
            return new ApiResponse("The commentary not found", false);

        commentaryRepository.deleteById(id);
        return new ApiResponse("The commentary deleted", true);
    }
}
