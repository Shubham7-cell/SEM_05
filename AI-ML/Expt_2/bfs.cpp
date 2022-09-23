#include <bits/stdc++.h>
using namespace std;

vector <vector <int>> v;
vector <vector <vector<int>>> vis;

class state
{
    public:
        vector<vector<int>> node;
        vector<vector<vector<int>>>path;
};


vector <vector <int>> up(vector<vector<int>> x)
{
    int r,c;
    for(int i=0;i<4;i++)
    {
        for(int j=0;j<4;j++)
        {
            if(x[i][j]==0)
            {
                r=i;
                c=j;
            }
        }
    }
    if(r!=0)
    {
        swap(x[r-1][c],x[r][c]);
    }
    return x;
}

vector <vector <int>> down(vector<vector<int>> x)
{
    int r,c;
    for(int i=0;i<4;i++)
    {
        for(int j=0;j<4;j++)
        {
            if(x[i][j]==0)
            {
                r=i;
                c=j;
            }
        }
    }
    if(r!=3)
    {
        swap(x[r+1][c],x[r][c]);
    }
    return x;
}

vector <vector <int>> left(vector<vector<int>> x)
{
    int r,c;
    for(int i=0;i<4;i++)
    {
        for(int j=0;j<4;j++)
        {
            if(x[i][j]==0)
            {
                r=i;
                c=j;
            }
        }
    }
    if(c!=0)
    {
        swap(x[r][c-1],x[r][c]);
    }
    return x;
}

vector <vector <int>> right(vector<vector<int>> x)
{
    int r,c;
    for(int i=0;i<4;i++)
    {
        for(int j=0;j<4;j++)
        {
            if(x[i][j]==0)
            {
                r=i;
                c=j;
            }
        }
    }
    if(c!=3)
    {
        swap(x[r][c+1],x[r][c]);
    }
    return x;
}

bool compare(vector <vector<int>> x)
{
    vector<vector <int>> vect{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
    if(x==vect)
    {
        return true;
    }
    else
    {
        return false;
    }
}

bool present(vector<vector <int>>x)
{
    for(int i=0;i<vis.size();i++)
    {
        if(x==vis[i])
        {
            return true;
        }
    }
    return false;
}

void printp(vector<vector<int>> x)
{
    for(int i=0;i<4;i++)
    {
        for(int j=0;j<4;j++)
        {
            cout<<x[i][j]<<"\t";
        }
        cout<<endl;
    }
    cout<<endl;
}

int inversion(vector<vector<int>> x){
    int count=0;
    vector<int>arr;
    for(int i=0;i<4;i++)
    {
        for(int j=0;j<4;j++)
        {
                arr.push_back(x[i][j]);
        }
    }
    for (int i = 0; i < arr.size()-1; i++)
    {
        for (int j = i + 1; j < arr.size(); j++)
        {
            if (arr[j] && arr[i] && arr[i] > arr[j])
            {
                count++;
            }    
        }
    }
    return count;
}

int pos(vector<vector<int>> x)
{
    int result;
    for(int i=0;i<4;i++)
    {
        for(int j=0;j<4;j++)
        {
            if(x[i][j]==0)
            {
                result=i;
            }
        }
    }
    return result;
}

bool solvable(vector<vector<int>> x)
{
    int a = pos(x)%2;
    int b = inversion(x)%2;
    if(a!=b)
    {
        return true;
    }
    else
    {
        return false;
    }
}

void printpath(vector<vector<vector<int>>>x)
{
    for(int i=0;i<x.size();i++)
    {
        printp(x[i]);
    }
}

void solve()
{
    queue <state> q;
    state curr = state();
    curr.path.push_back(v);
    curr.node = v;
    q.push(curr);
    
    while(!q.empty())
    {
        state parent = q.front();
        q.pop();
        
        if(compare(parent.node))
        {
            printpath(parent.path);
            break;
        }
        
        if(!present(up(parent.node)) && solvable(up(parent.node)))
        {
            state child = state();
            child.node = up(parent.node);
            child.path = parent.path;
            child.path.push_back(child.node);
            q.push(child);
            vis.push_back(child.node);
        }
        
        if(!present(down(parent.node)) && solvable(down(parent.node)))
        {
            state child = state();
            child.node = down(parent.node);
            child.path = parent.path;
            child.path.push_back(child.node);
            q.push(child);
            vis.push_back(child.node);
        }
        
        if(!present(left(parent.node)) && solvable(left(parent.node)))
        {
            state child = state();
            child.node = left(parent.node);
            child.path = parent.path;
            child.path.push_back(child.node);
            q.push(child);
            vis.push_back(child.node);
        }
        
        if(!present(right(parent.node)) && solvable(right(parent.node)))
        {
            state child = state();
            child.node = right(parent.node);
            child.path = parent.path;
            child.path.push_back(child.node);
            q.push(child);
            vis.push_back(child.node);
        }
    }
}

int main()
{
    vector <int> test;
    int input;
    cout<<"Enter the 15-puzzle problem:\n";
    for(int i=0;i<4;i++)
    {
        test.clear();
        for(int j=0;j<4;j++)
        {
            cin>>input;
            test.push_back(input);
        }
        v.push_back(test);
    }
    cout<<"Your 15-puzzle problem input is: \n\n";

    if(solvable(v))
    {
        vis.push_back(v);
        solve();
    }
    
    return 0;
}

