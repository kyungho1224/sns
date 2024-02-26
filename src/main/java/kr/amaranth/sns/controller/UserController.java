package kr.amaranth.sns.controller;

import kr.amaranth.sns.controller.request.UserJoinRequest;
import kr.amaranth.sns.controller.response.Response;
import kr.amaranth.sns.controller.response.UserJoinResponse;
import kr.amaranth.sns.model.User;
import kr.amaranth.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    // TODO implement
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(request.getUserName(), request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }

}
