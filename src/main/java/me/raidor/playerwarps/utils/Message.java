package me.raidor.playerwarps.utils;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public enum Message {
    NO_PERMISSION("no_permission", "&cYou do not have permission to use this command!"),
    DEFAULT_WELCOME_MESSAGE("default_welcome_message", "&eThe warp message has not been set. Use /warp setmessage <name> <message> to set it!"),
    CREATE_USAGE("create_usage", "&cUsage: /warp create <name> [password]"),
    CREATE_MAX("create_max", "&cYou cannot create another warp!"),
    CREATE_MAX_NAME("create_max_name", "&cThe warp name cannot exceed {0} characters!"),
    CREATE_SUCCESS("create_success", "&aWarp '{0}' was successfully created."),
    CREATE_EXISTS("create_exists", "&cThis warp name already exists!"),
    DELETE_USAGE("delete_usage", "&cUsage: /warp delete <name>"),
    DELETE_SUCCESSFUL("delete_successful", "&cWarp '{0}' was successfully deleted."),
    DELETE_NOT_OWNER("delete_not_owner", "&cYou do not own this warp!"),
    DELETE_NOT_FOUND("delete_not_found", "&cThis warp does not exist!"),
    LIST_HEADER("list_header", "&aAvailable Warps:"),
    LIST_WARP("list_warp", "&7 -> &e{0} &7({1}&7) &8- &fSet by {2}"),
    LIST_FOOTER("list_footer", "&aViewing page &2{0}&a/&2{1}"),
    LIST_INVALID_PAGE("list_invalid_page", "&cThis page does not exist!"),
    LIST_HOVER_TEXT("list_hover_text", "&eClick here to visit!"),
    REMOVE_PASSWORD_USAGE("remove_password_usage", "&cUsage: /warp removepassword <name>"),
    REMOVE_PASSWORD_NOT_OWNER("remove_password_not_owner", "&cYou do not own this warp!"),
    REMOVE_PASSWORD_SUCCESSFUL("remove_password_complete", "&aPassword for warp '{0}' was successfully removed."),
    REMOVE_PASSWORD_NOT_FOUND("remove_password_not_found", "&cThis warp does not exist!"),
    SET_MESSAGE_USAGE("set_message_usage", "&cUsage: /warp setmessage <name> <message>"),
    SET_MESSAGE_TOO_LONG("set_message_too_long", "&cThe message cannot exceed {0} characters!"),
    SET_MESSAGE_NOT_OWNER("set_message_not_owner", "&cYou do not own this warp!"),
    SET_MESSAGE_SUCCESSFUL("set_message_successful", "&aMessage for warp '{0}' was set to:"),
    SET_MESSAGE_NOT_FOUND("set_message_not_found", "&cThis warp does not exist!"),
    SET_PASSWORD_USAGE("set_password_usage", "&cUsage: /warp setpassword <name> <password>"),
    SET_PASSWORD_TOO_LONG("set_password_too_long", "&cThe password cannot exceed {0} characters!"),
    SET_PASSWORD_NOT_OWNER("set_password_not_owner", "&cYou do not own this warp!"),
    SET_PASSWORD_SUCCESSFUL("set_password_successful", "&aSuccessfully set password for warp '{0}'."),
    SET_PASSWORD_NOT_FOUND("set_password_not_found", "&cThis warp does not exist!"),
    VISIT_USAGE("visit_usage", "&cUsage: /warp visit <name>"),
    VISIT_UNSAFE("visit_unsafe", "&cThis warp is not safe to warp to."),
    VISIT_NEEDS_PASSWORD("visit_needs_password", "&cThis warp requires a password! (/warp visit <name> <password>)"),
    VISIT_INCORRECT_PASSWORD("visit_incorrect_password", "&cThe password you entered is incorrect!"),
    VISIT_SUCCESSFUL("visit_successful", "&aTeleporting to warp '{0}':"),
    VISIT_NOT_FOUND("visit_not_found", "&cThis warp does not exist!"),
    HELP_HEADER("help_header", "&aPlayer Warps &8- &a(&7Version {0}&a):"),
    HELP_MESSAGE("help_message", Arrays.asList(
            "&f/warp visit <name> [password]",
            "&f/warp create <name> [password]",
            "&f/warp delete <name>",
            "&f/warp list [page]",
            "&f/warp setpassword <name> [password]",
            "&f/warp removepassword <name>",
            "&f/warp setmessage <name> <message>"
    )),
    UNKNOWN_COMMAND("unknown_command", "&cUnknown command, please use /warp help!");

    private String path, def;
    private List<String> list;
    private static FileConfiguration configuration;

    Message(String path, String def) {
        this.path = path;
        this.def = def;
    }

    Message(String path, List<String> list) {
        this.path = path;
        this.list = list;
    }

    @Override
    public String toString() {
        return Chat.format(configuration.getString(path, def));
    }

    public String toUnformattedString() { return Chat.string(configuration.getString(path, def)); }

    public List<String> toList() {
        return Chat.format(configuration.getStringList(path));
    }

    public static void setConfiguration(FileConfiguration configuration) {
        Message.configuration = configuration;
    }

    public String getPath() {
        return path;
    }

    public String getDef() {
        return def;
    }

    public List<String> getList() {
        return list;
    }
}
