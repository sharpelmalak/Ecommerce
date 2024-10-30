import os

def rename_images(directory):
    # Change the current working directory to the specified directory
    os.chdir(directory)
    
    # Get a list of all files in the directory
    files = os.listdir(directory)
    
    # Filter out only .jpg and .png files
    image_files = [file for file in files if file.lower().endswith(('.jpg', '.png'))]

    # Rename files
    for count, file in enumerate(image_files, start=1):
        # Define the new file name
        new_name = f"{count}.jpg"
        
        # Rename the file
        os.rename(file, new_name)
        print(f"Renamed '{file}' to '{new_name}'")

# Specify the directory containing the images
directory_path = '.'  # Update this path
rename_images(directory_path)
