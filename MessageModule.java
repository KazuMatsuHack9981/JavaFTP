public class MessageModule {
    public void print_banner() {
        System.out.println("\u001b[00;33m    _                    \u001b[00;31m_____ _____ ____  \u001b[00m");
        System.out.println("\u001b[00;33m    | | __ ___   ____ _  \u001b[00;31m|  ___|_   _|  _ \\ \u001b[00m");
        System.out.println(" _  | |/ _` \\ \\ / / _` | | |_    | | | |_) |");
        System.out.println("\u001b[00;33m| |_| | (_| |\\ V / (_| | \u001b[00;31m|  _|   | | |  __/ \u001b[00m");
        System.out.println("\u001b[00;33m \\___/ \\__,_| \\_/ \\__,_| \u001b[00;31m|_|     |_| |_|    \u001b[00m");
        System.out.println("\n  * version 1.0 created for report * \n\n");
    }

    public void newline(int n) {
        for(int i=0; i<n; i++) System.out.println();
    }

    public void print_log(String s) {
        System.out.println("[*] "+s);
    }

    public void print_err(String s) {
        System.out.println("\u001b[00;31m[-]\u001b[00m "+s);
    }

    public void print_ok(String s) {
        System.out.println("\u001b[00;32m[+]\u001b[00m "+s);
    }
}
