{
  pkgs,
  ...
}:

{
  # https://devenv.sh/basics/
  env.GREET = "devenv";

  # https://devenv.sh/packages/
  packages = [
    pkgs.git
    pkgs.just
  ];

  # JavaFX native libraries (glassgtk3/prism_es2) are dlopen'd from
  # ~/.openjfx/cache without RPATH, so they must be reachable via
  # LD_LIBRARY_PATH on NixOS.
  env.LD_LIBRARY_PATH = pkgs.lib.makeLibraryPath [
    pkgs.gtk3
    pkgs.glib
    pkgs.pango
    pkgs.atk
    pkgs.cairo
    pkgs.gdk-pixbuf
    pkgs.xorg.libXtst
    pkgs.xorg.libX11
    pkgs.xorg.libXxf86vm
    pkgs.libGL
    pkgs.fontconfig
    pkgs.freetype
  ];
  delta.enable = true;

  languages = {
    java = {
      jdk.package = pkgs.jdk25;
      enable = true;
      gradle = {
        enable = true;
        package = pkgs.gradle_9;
      };
      lsp.enable = true;
    };
    # kotlin = {
    #   enable = true;
    #   lsp.enable = true;
    # };
  };

  # https://devenv.sh/processes/
  # processes.dev.exec = "${lib.getExe pkgs.watchexec} -n -- ls -la";

  # https://devenv.sh/services/
  # services.postgres.enable = true;

  # https://devenv.sh/scripts/
  scripts.hello.exec = ''
    echo hello from $GREET
  '';

  # https://devenv.sh/basics/
  enterShell = ''
    hello         # Run scripts directly
    git --version # Use packages
  '';

  # https://devenv.sh/tasks/
  # tasks = {
  #   "myproj:setup".exec = "mytool build";
  #   "devenv:enterShell".after = [ "myproj:setup" ];
  # };

  # https://devenv.sh/tests/
  enterTest = ''
    echo "Running tests"
    git --version | grep --color=auto "${pkgs.git.version}"
  '';

  # https://devenv.sh/git-hooks/
  # git-hooks.hooks.shellcheck.enable = true;

  # See full reference at https://devenv.sh/reference/options/
}
