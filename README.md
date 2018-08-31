# Passbench

Passbench is a highly configurable password benchmarking tool that you can use to understand how secure a password is against a brute-force attack. In theory, you could use this program to generate a UTF-8 string of any length (within reason) that can contain numbers, letters, and symbols in any combination. 

This is a project I'm doing for experience. I plan to update frequently until the final, completely comprehensive version.

Passbench, a Password Benchmarking Tool, is completely functional from this initial release, and will grow more comprehensive in time.

Ultimately, Passbench is made with speed, and efficiency in mind in order to provide the most realistic results.

## Currently Featured

- Passbench keeps track of how long it takes cracking the supplied password, giving you the exact time it took after it's finished with a benchmark, even if it takes days.

- Passbench allows you to choose how many threads it uses to benchmark the password, allowing you to unleash the full power of your CPU, and giving you all the power.
  > My suggestion for a Thread count is to experiment with it yourself, as this is a benchmarking tool. I personally suggest that you use   the number of threads(not physical cores) minus 1, or, you can just use the number of physical cores your CPU has. This has yielded the   best results for my machine.

- The following Password Specifications are valid in Passbench
  - Numbers
  
  - Letters
  
  - Numbers and Letters
  
  - Symbols, Numbers, and Letters
