package edu.tcu.cs.frogcrewonline.invite;

import edu.tcu.cs.frogcrewonline.system.Result;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// handles crew invitations

@RestController
@RequestMapping("/invite")
public class InviteController {

    private final InviteService inviteService;

    public InviteController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @PostMapping
    public Result sendInvites(@Valid @RequestBody EmailDto dto) {
        List<String> sent = inviteService.sendInvites(dto.getEmails());
        return new Result(true, StatusCode.SUCCESS, "Invite Success", sent);
    }
}