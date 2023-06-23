import git
import datetime
import subprocess

def modify_commit_timestamp(commit_hash, timestamp):
    # Amend the commit with the desired timestamp
    subprocess.run(['git', 'commit', '--amend', '--no-edit', '--date', timestamp])
    print(f"Modified commit {commit_hash} with timestamp {timestamp}")

def simulate_daily_commits(repo_url, branch_name, access_token):
    # Clone the repository
    repo = git.Repo.clone_from(repo_url, 'temp_repo')
    repo.git.checkout('-b', branch_name)

    start_date = datetime.datetime(2022, 1, 1)
    end_date = datetime.datetime(2023, 1, 5)

    # Identify the commits you want to publish day by day
    commit_hashes = ['commitA', 'commitB', 'commitC', 'commitD', 'commitE']
    commit_timestamps = []
    current_date = start_date

    while current_date <= end_date:
        commit_timestamps.append(current_date.strftime("%Y-%m-%d %H:%M:%S"))
        current_date += datetime.timedelta(days=1)

    for commit_hash, timestamp in zip(commit_hashes, commit_timestamps):
        # Checkout the commit
        repo.git.checkout(commit_hash)

        # Modify the commit timestamp
        modify_commit_timestamp(commit_hash, timestamp)

        # Continue the rebase process
        repo.git.rebase('--continue')

    # Push the new branch to GitHub
    remote = repo.remote()
    remote_url_with_token = repo_url.replace('https://', f'https://{access_token}@')
    remote.set_url(remote_url_with_token)
    remote.push(branch_name)

    print(f"Daily commits simulation completed. Branch '{branch_name}' pushed to GitHub.")

    # Clean up the temporary repository
    repo.close()
    subprocess.run(['rm', '-rf', 'temp_repo'])

# Example usage
repo_url = 'https://github.com/FanitoApp/Fanito-AndroidClient.git'
branch_to_modify = 'main'
personal_access_token = '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'
simulate_daily_commits(repo_url, branch_to_modify, personal_access_token)




