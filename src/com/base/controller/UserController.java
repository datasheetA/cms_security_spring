package com.base.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.base.model.Role;
import com.base.model.User;
import com.base.service.RoleService;
import com.base.service.UserService;
import com.base.tree.UserInfo;
import com.base.util.PageNavUtil;
import com.base.util.PageUtil;
import com.base.util.ResponseUtil;
import com.base.util.UserUtil;
import com.base.vo.PageData;
import com.base.vo.UserRole;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@RequestMapping("/list1")
	@ResponseBody
	public Object list() {
		return userService.findall();
	}

	@RequestMapping(value = "/findindex")
	public ModelAndView findindex(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/views/limx/user");
		return view;
	}

	@RequestMapping(value = "/index")
	public String index() {
		return "/index";
	}

	@RequestMapping(value = "/form")
	public String form() {
		return "/views/limx/form";
	}

	@RequestMapping(value = "/bootstraptreeview")
	public String bootstraptreeview() {
		return "/views/limx/bootstraptreeview";
	}

	@RequestMapping(value = "/tree")
	public String tree() {
		return "/views/limx/tree";
	}

	@RequestMapping("/findbypage")
	@ResponseBody
	public Object findbypage(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int rows) {
		return PageUtil.convertGrid(userService.findbypage(page, rows));
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(HttpServletRequest request, HttpSession session, String name, String email,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println("开始");
		String path = request.getSession().getServletContext().getRealPath("upload");
		String fileName = file.getOriginalFilename();
		// String fileName = new Date().getTime()+".jpg";
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		System.out.println(path + "/" + fileName);
		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("fileaddress", "upload/" + fileName);
		return map;
	}

	// ------------------------------------------------------!!!!!----------------------------------------------
	/**
	 * 新增用户
	 * 
	 * @Title: add @Description: 新增用户 @param @param user 用户对象 @return Object
	 *         返回类型 @throws
	 */
	@RequestMapping(value = "/add")
	public @ResponseBody Object add(String loginName, String displayName, String contactPhone, String password,
			@RequestParam(required = false) int[] roleIdAdd) {
		User user = new User();
		user.setLoginName(loginName);
		user.setDisplayName(displayName);
		user.setContactPhone(contactPhone);
		user.setPassWord(password);
		userService.insertUser(user);
		if (roleIdAdd != null && roleIdAdd.length != 0) {
			for (int roleId : roleIdAdd) {
				userService.insertUserRole(user.getId(), roleId);
			}
		}
		return ResponseUtil.success();
	}

	/**
	 * 新增用户
	 * 
	 * @Title: add @Description: 新增用户 @param @param user 用户对象 @return Object
	 *         返回类型 @throws
	 */
	@RequestMapping(value = "/updateUser")
	public @ResponseBody Object updateUser(User user) {
		if (user.getId() == 0) {
			userService.insertUser(user);
		} else {
			userService.updateUser(user);
		}
		return ResponseUtil.success();
	}

	/**
	 * 删除用户
	 * 
	 * @Title: delete @Description: 删除用户 @param @param id userId @param @param
	 *         loginName 账号 @return Object 返回类型 @throws
	 */
	@RequestMapping(value = "/delUser")
	public @ResponseBody Object delete(@RequestParam(value = "id") int id) {
		org.springframework.security.core.userdetails.User user = UserUtil.findSessionUser();
		userService.deleteUser(id, user.getUsername());
		return true;
	}

	/**
	 * 删除用户
	 * 
	 * @Title: delete @Description: 删除用户 @param @param id userId @param @param
	 *         loginName 账号 @return Object 返回类型 @throws
	 */
	@RequestMapping(value = "/deleteAll")
	public @ResponseBody Object deleteAll(@RequestParam(value = "ids[]", required = false) String[] ids,
			@RequestParam(value = "loginName", required = false) String loginName) {
		System.out.println("ids=" + ids);
		org.springframework.security.core.userdetails.User user = UserUtil.findSessionUser();
		if (ids != null && ids.length != 0) {
			for (String id : ids) {
				userService.deleteUser(Integer.valueOf(id), user.getUsername());
			}
		}
		return ResponseUtil.success();

	}

	/**
	 * 更新用户信息
	 * 
	 * @Title: update @Description: 更新用户信息 @param @param user 用户对象 @return
	 *         Object 返回类型 @throws
	 */
	@RequestMapping(value = "/update")
	public @ResponseBody Object update(String loginName, String displayName, int id, String contactPhone,
			@RequestParam(required = false) int[] roleIdModify) {
		User user = new User();
		user.setId(id);
		user.setLoginName(loginName);
		user.setContactPhone(contactPhone);
		user.setDisplayName(displayName);
		userService.updateUser(user);
		if (roleIdModify != null && roleIdModify.length != 0) {
			userService.deleteUserRole(user.getId());
			for (int roleId : roleIdModify) {
				userService.insertUserRole(user.getId(), roleId);
			}
		}
		return ResponseUtil.success();
	}

	@RequestMapping(value = "/mail")
	public @ResponseBody Object sendRestMail(HttpServletRequest req, @RequestParam("loginName") String loginName) {

		return ResponseUtil.success();
	}

	/**
	 * 
	 * @Title: reset @Description: 重置密码 @param @param req @param @param
	 *         loginName @param @return @return Object 返回类型 @throws
	 */
	@RequestMapping(value = "/reset")
	public @ResponseBody Object reset(HttpServletRequest req, @RequestParam("password") String password) {
		return ResponseUtil.success();
	}

	/**
	 * 用户角色分配
	 * 
	 * @Title: checkRoles @Description: 用户角色分配 @param @param id
	 *         userID @param @param roleIds 角色ID集合 @return Object 返回类型 @throws
	 */
	@RequestMapping("/checkRoles")
	public @ResponseBody Object checkRoles(@RequestParam(value = "id") int id,
			@RequestParam(value = "roleIds") String roleIds) {

		userService.updateRoleschecked(id, roleIds);
		return ResponseUtil.success();
	}

	/**
	 * 冻结、解冻用户
	 * 
	 * @Title: frozen @Description: 冻结、解冻用户 @param @param id
	 *         userId @param @param state 用户当前状态 @return Object 返回类型 @throws
	 */
	@RequestMapping(value = "/frozen", method = RequestMethod.POST)
	public @ResponseBody Object frozen(@RequestParam("id") int id, @RequestParam("state") int state) {
		userService.updateState(id, state);
		return true;

	}

	/**
	 * 修改密码
	 * 
	 * @Title: changePwd @Description: 修改密码 @param @param oldPassword
	 *         旧密码 @param @param password 新密码 @return Object 返回类型 @throws
	 */
	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public @ResponseBody Object changePwd(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("password") String password) {
		userService.updatePassword("limingxing", oldPassword, password);
		return ResponseUtil.success();

	}

	/**
	 * 用户分配角色
	 * 
	 * @Title: roleList @Description: 用户分配角色 @param @param id userId @return
	 *         Object 返回类型 @throws
	 */
	@RequestMapping("/roleList")
	public @ResponseBody Object roleList(@RequestParam(value = "id") int id) {

		List<UserRole> list = userService.findRoleList(id);
		return list;
	}

	@RequestMapping("/list")
	public String userList(Model model, Integer page, String userName) {

		page = page == null ? 1 : page < 1 ? 1 : page;

		int pageSize = 10;
		User user = new User();
		user.setDisplayName(userName);
		user.setLoginName(userName);
		PageData<User> pageData = userService.findUserPageList(user, page, pageSize);

		model.addAttribute("dataList", pageData.getPageData());
		model.addAttribute("totalCount", pageData.getTotalCount());
		model.addAttribute("totalPage", pageData.getTotalPage());
		model.addAttribute("currentPage", page);
		model.addAttribute("pageNav",
				PageNavUtil.getPageNavHtml(page.intValue(), pageSize, pageData.getTotalCount(), 15));

		return "ucenter/sys/userList";
	}

	@RequestMapping(value = "/name")
	public @ResponseBody Object getName() {
		String loginName = "liminxing";
		User u = userService.findUserByLoginName(loginName);
		return ResponseUtil.success(u.getDisplayName());

	}

	@RequestMapping("/viewUser")
	public String detail(Model model, String id) {
		User userEntity = new User();
		if (Integer.parseInt(id) == 0) {
			userEntity.setState(1);
		} else {

			userEntity = userService.findUser(Integer.parseInt(id));

		}
		model.addAttribute("userEntity", userEntity);

		return "ucenter/sys/userEdit";
	}

	@RequestMapping("/userRoleSet")
	public String userRoleSet(Model model, int userId) {
		// 取用户
		User userEntity = this.userService.findUser(userId);
		// 取所有角色
		List<Role> allRoles = this.roleService.findAllEffectiveRoles();

		UserInfo userInfo = new UserInfo();
		userInfo.setId(userEntity.getId());
		userInfo.setUserName(userEntity.getDisplayName());
		List<Role> roles = new ArrayList<Role>();
		for (Role roleEntity : roleService.findRolesByUserId(userEntity.getId())) {
			userInfo.getRoles().add(roleEntity.getId());
		}
		model.addAttribute("allRoles", allRoles);

		model.addAttribute("userInfo", userInfo);

		return "ucenter/sys/userRoleSet";
	}

	@RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveUserRole(Model model, UserInfo userInfo) {

		int userId = userInfo.getId();
		if (userId <= 0) {
			return ResponseUtil.fail("没有指定用户");
		}
		userService.deleteUserRole(userInfo.getId());
		if (userInfo.getRoles() != null) {
			for (Integer roleId : userInfo.getRoles()) {
				userService.insertUserRole(userInfo.getId(), roleId);
			}
		}
		return ResponseUtil.success();

	}
}
