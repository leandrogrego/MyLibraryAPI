package br.net.serviceapp.mylibrary.api.resource;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.net.serviceapp.mylibrary.api.Service.EmailService;
import br.net.serviceapp.mylibrary.api.Service.UserService;
import br.net.serviceapp.mylibrary.api.entity.Response;
import br.net.serviceapp.mylibrary.api.entity.User;
import br.net.serviceapp.mylibrary.api.tools.Cript;
import br.net.serviceapp.mylibrary.api.tools.WebTools;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("users")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired 
	private EmailService emailService;

	// Prazo de validade do link de redefinição de senha
	long prazo = 60; // minutos

	//Cadastro e validação de e-mail
	@PostMapping("/validate")
	public Response sendCode(@RequestParam String email) throws Exception {
		User user = userService.findByEmail(email); 
		if(user == null){
			user = new User();
			user.setEmail(email);
			int time = (int) new Date().getTime();
			String token = Cript.hash(""+time+email);
			user.setToken(token);
        }
		user.setLastAccess(new Date());
		return sendCode(user);
	}
	
	//Envia e-mail com código de validação
	public Response sendCode(User user) throws Exception{
		if(user != null) {
			String code = generateCode();
			user.setCode(code);
			userService.save(user);
			try {
				System.out.println("Enviando e-mail para "+user.getEmail());
				emailService.send(
					"noreply@serviceapp.net.br",
					user.getEmail(),
					"Validação de login no sistema MyLibrary",
					"Esse é um email de validação de login do sistema Mylibrary. \n"
					+"Este código tem validade de " + prazo + " minutos. \n"
					+"Para autorizar o login, digite o códog de verificação abaixo no aplicativo: \n\n"
					+"Codigo: "+code+" \n\n"
					+ "\n\nEquipe ServiceApp"
				);
				return new Response(true, "Email com código de verificaçao enviado com sucesso!", user.getEmail());
			} catch (Exception e) {
				return new Response(false, "Erro ao enviar e-mail!", null);
			}
		} else {
			return new Response(false, "E-ail nãoo localizado!", null);
		}
	}

	// Validação de acesso
	@PutMapping("/validate")
	public Response sendColde(@RequestParam String email, @RequestParam String code) throws Exception {
		User user = userService.findByEmail(email); 
		long time = user.getLastAccess().getTime();
		int now = (int) ((new Date().getTime()-time)/1000/60);
		if(user != null &&  user.checkCode(code) && now<=prazo){
			//String token = Cript.hash(time+email+code);
			//user.setToken(token);
			String token = user.getToken();
			user.setCode(null);
			user.setEmailValidated(true);
			user.setLastAccess(new Date());
			userService.save(user);
			return new Response(true, "Código Validado com sucesso!", token);
		}
		return new Response(false, "Código de validação inválido!", null);
	}

	//Desconectar todos os dispositivos
	@DeleteMapping("/validate")
	public Response logOut
	(@RequestHeader(value="Authorization") String token
	){
		User user = userService.getByToken(token);
		if(user !=  null) {
			token = Cript.hash(token);
			user.setToken(token);
			userService.save(user);
			return new Response(true, "LogOut efetuado com sucesso!", null);
		} else return new Response(false, "Token inválido ou expirado!", null);
	}

	// Dados do usuario
	@GetMapping
	public Response getByToken(
		@RequestHeader(value="Authorization") String token
	){
		User user = userService.getByToken(token);
		if(user !=  null) return new Response(true, "", user);
		else return new Response(false, "Token inválido ou expirado!", null);
	}

	//atualiza dados do usuário
	@PutMapping
	public Response update(
		@RequestHeader(value="Authorization") String token,
		@RequestBody User user
		){
		User u = userService.getByToken(token);
		if(u != null ) {
			//mangetem id toke e senha anteriores
			user.setToken(token);
			user.setId(u.getId());
			user.setPassword(u.getPassword());
			user = userService.save(user);

			return new Response(false, "usuário Salvo com sucesso!", user);
		} else return new Response(false, "Dados do usuário ou Token inválido!", null);
	}

	public String generateCode(){
        Random gerador = new Random();
        int code = 0;
        for(int i = 0; i<6; i++){
            int v = gerador.nextInt() % 10;
			if(v < 0) v *= -1;
            code = (code * 10) + v;
        }
        return ""+code;
    }






	  /////////////////////////////
	 // DEPRECATED  - TO REMOVE //
	/////////////////////////////

    //Solicta envio de email de recurperação de senha
    @PostMapping("/recovery")
	public Response sendLink(@RequestParam String email) throws Exception {
		User user = userService.findByEmail(email); 
		return sendLink(user);
	}

	//Envia e-mail de recuperação de senha
    public Response sendLink(User user) throws Exception{
		if(user != null) {
            String email = user.getEmail();
			String password = user.getPassword();
			long time = new Date().getTime();
			String hash = Cript.hash(""+email+password+time);
			String host = "localhost:7100";
			String link = "http://"+host+"/recovery/"+user.getEmail()+"/"+time+"/"+hash;
			try {
				System.out.println("Enviando e-mail para "+user.getEmail());
				emailService.send(
    				"noreply@serviceapp.net.br",
    				user.getEmail(),
    				"Redefinição de senha MyLibrary",
    				"Esse é um email de recuperação de senha do sistema Mylibrary. \n"
    				+"Para redefinir sua senha acesse o link: \n"
    				+ link
					+ "\n\nEquipe ServiceApp"
		        );
				return new Response(true, "Email de redefinição de senha enviado com sucesso.", user.getEmail());
			} catch (Exception e) {
				return new Response(false, "Erro ao enviar e-mail!", null);
			}
		} else {
			return new Response(false, "E-ail nãoo localizado!", null);
		}
	}

    //Verifica link de recuperação de senha
	@GetMapping("/recovery/{email}/{time}/{hash}")
	public Response LinkValidate(
			@PathVariable String email,
			@PathVariable long time,
			@PathVariable String hash
		) throws Exception {
		User user = userService.findByEmail(email); 
		if(user != null){
			String password = user.getPassword();
			String check = Cript.hash(""+email+password+time);
			int tempo = (int) ((new Date().getTime()-time)/1000/60);
			if(hash.equals(check) && tempo<=prazo) {
				user.setEmailValidated(true);
				user = userService.save(user);
				return new Response(true, "", user);
			};
		}
		return new Response(false, "Link de redefinição de senha inválido ou expirado!", null);
	}
	
    //Redefinição de senha
	@PutMapping("/recovery")
	public Response SetPassword(
			@RequestParam String email,
			@RequestParam long time,
			@RequestParam String hash,
			@RequestParam String newPassword
		) throws Exception {
		
		User user = userService.findByEmail(email); 
		if(user != null) {
			String password = user.getPassword();
			String check = Cript.hash(""+password+time);
			int tempo = (int) ((new Date().getTime()-time)/1000/60);
			if(hash.equals(check) && tempo<=prazo) {
				user.setPassword(newPassword);
				userService.save(user);
				return new Response(true, "", user);
			};
		}
		return new Response(false, "Link de redefinição de senha inválido ou expirado!", null);
	}

	//login por Senha
    @PostMapping("/login")
	public Response login(
            @RequestParam String email,
            @RequestParam String password
        ) throws Exception{
        User user = userService.findByEmail(email);
        long time = new Date().getTime();
        String token = Cript.hash(time+email+password);
        if(password.equals(user.getPassword())){
            user.setToken(token);
            userService.save(user);
            return new Response(true, "", token);
        }
        return new Response(false, "E-mail ou Senha inválidos!", null);
    }

	//login por rede Social
    @GetMapping("/login/google")
	public User loginGoogle(
			@RequestHeader(value="Authorization") String token
			) {
        User user = null;        
		System.out.println(token);
		if(!token.equals(null) && !token.equals("")) {
            try {
                Object profile = WebTools.get("https://www.googleapis.com/oauth2/v3/userinfo?access_token="+token, null);
                if(profile != null) {
                    String socialId = profile.getClass().getField("sub").toString();
                    String name = profile.getClass().getField("name").toString();
                    String email = profile.getClass().getField("meail").toString();
                    String avatar_url = profile.getClass().getField("picture").toString();
                    user = userService.socialLogin(socialId, "google", token, name, email, avatar_url);
                } 
            }catch (NoSuchFieldException | SecurityException e) {
                    e.printStackTrace();
			}
		}
		return user;
	}

	//Verifica se e-mail não está cadastrado
    @GetMapping("/join")
	public Response checkEmail(
            @RequestParam("email") String email
			) {
		// retorna true se e-mail não existir	
        boolean success = (userService.findByEmail(email) == null);
        String message = "";
        if(!success) message = "E-mail já cadastrado!\nSolicite redefinição de senha.";
        return new Response(success, message, null);
    }

	//Cadastro de usuário
    @PostMapping("/join")
	public Response create(
            @RequestParam("email") String email
			) throws Exception {
        User user = userService.findByEmail(email);
        if(user != null){
            return new Response(false, "E-mail já cadastrado!\nSolicite redefinição de senha.", null);
        }
        user = new User();
        user.setEmail(email);
        user = userService.save(user);
        sendLink(user);
        return new Response(true, "Enviamos uma mensagem de validação para o endereço "+email+".\nVerifique sua caixa de entrada.", user);
    }

}
