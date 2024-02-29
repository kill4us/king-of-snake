import { gameObject } from "./gameObject";
import { Cell } from "./Cell";

export class Snake extends gameObject {
    constructor(info, gamemap) {
        super();
        this.id = info.id;
        this.color = info.color;
        this.gamemap = gamemap;  //  取出地图的引用，可以用来调用一些地图的函数

        this.next_cell = null;  //  下一步的目的地

        this.cells = [new Cell(info.r, info.c)];  //  存放蛇的身体,cells[0]存放蛇头
        this.speed = 5;  //  蛇每秒钟走五个格子

        this.direction = -1;  //  蛇下一步的指令，-1表示没有指令, 0, 1, 2, 3表示上右下左
        this.status = "idle"  //  蛇当前的状态，idle表示静止，move表示移动，die表示死亡

        this.dr = [-1, 0, 1, 0];  //  行的偏移量  上右下左
        this.dc = [0, 1, 0, -1];  //  列的偏移量

        this.eps = 1e-2;  //  误差量
        this.step = 0;  //  表示当前的回合数


        this.eye_direction = 0;  //  蛇眼睛的方向 0, 1, 2, 3   上右下左
        if (this.id === 1) {
            this.eye_direction = 2;
        }

        this.eye_dx = [  //  蛇眼睛横坐标偏移量
            [-1, 1],
            [1, 1],
            [-1, 1],
            [-1, -1],
        ]

        this.eye_dy = [  //  蛇眼睛纵坐标偏移量
            [-1, -1],
            [-1, 1],
            [1, 1],
            [-1, 1],
        ]
    }

    start() {

    }

    set_direction(d) {  //  统一接口，用来设置方向   
        this.direction = d;

    }

    check_tail_increasing() {  //  判断蛇尾是否增加
        if (this.step <= 10) {  //  前10回合蛇的长度必定增加，游戏规则限制
            return true;
        }
        if (this.step % 3 === 1) return true;  //  之后每三回合蛇的长度增加1
        return false;
    }

    next_step() {  //  将蛇的状态变为走下一步
        const d = this.direction;  //  取出当前的方向
        this.next_cell = new Cell(this.cells[0].r + this.dr[d], this.cells[0].c + this.dc[d]);  //  计算下一个目的地
        this.eye_direction = d;  //  更新蛇头的方向
        this.direction = -1;  //  用完方向后，清空
        this.status = "move";  //  更新蛇的状态
        this.step ++;  //  进入下一个回合，回合数 ++

        const k = this.cells.length;  //  求所有小球的数量
        for (let i = k; i > 0; i --) {  //  把每个小球向后移动一位，第一位不变，相当于头部多了一个自己的复制   1   2   3
            this.cells[i] = JSON.parse(JSON.stringify(this.cells[i - 1]));  //  深层复制一遍，避免混淆     1   1   2    3
        }
        if (!this.gamemap.check_valid(this.next_cell)) {  //  下一步操作非法
            this.status = "die";
        }
    }

    update_move() {  //  更新蛇的移动  从蛇头创建一个新的头，让新的头向目的地移动 蛇的身体不变
        const dx = this.next_cell.x - this.cells[0].x;  //  x方向偏移
        const dy = this.next_cell.y - this.cells[0].y;  //  y方向偏移
        const distance = Math.sqrt(dx * dx + dy * dy);  //  移动的总距离

        if (distance < this.eps)  //  当两个点的距离已经在误差范围内，就认为两个点已经重合了
        {

            this.cells[0] = this.next_cell;  //  将目标点作为我们新的蛇头
            this.next_cell = null;  //  清空目标点，完成移动
            this.status = "idle";  //  更新蛇的状态为静止

            if(!this.check_tail_increasing()) {  //  如果蛇不变长
                this.cells.pop();  //  弹出蛇尾
            }
        }
        else {  //  如果不重合，就要移动了
            const move_distance = this.speed * this.timedelta / 1000;  //  每一帧移动的距离
            this.cells[0].x += move_distance * dx / distance;
            this.cells[0].y += move_distance * dy / distance;

            if (!this.check_tail_increasing()){  //  如果蛇尾不变长，那么蛇尾就需要走到下一个目的地
                const k = this.cells.length;  //  取出蛇的长度
                const tail = this.cells[k - 1];  //  取出蛇尾
                const tail_target = this.cells[k - 2];  //  蛇尾的目的地
                const tail_dx = tail_target.x - tail.x;  //  横坐标的差值
                const tail_dy = tail_target.y - tail.y;  //  纵坐标的差值
                tail.x += move_distance * tail_dx / distance;
                tail.y += move_distance * tail_dy / distance;
            }
        }
    }

    update(){  //  每一帧执行一次
        if(this.status === 'move') {
            this.update_move();
        }
        this.render();
    }
    
    render() {
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;

        ctx.fillStyle = this.color;

        if (this.status === "die") {
            ctx.fillStyle = "white";
        }
        //  枚举一下蛇的每个身体
        for (const cell of this.cells)
        {
            ctx.beginPath();
            ctx.arc(cell.x * L, cell.y * L, L / 2 * 0.8, 0, Math.PI * 2);
            ctx.fill();
        }

        for (let i = 1; i < this.cells.length; i ++ )
        {
            const a = this.cells[i - 1], b = this.cells[i];
            if (Math.abs(a.x - b.x) < this.eps && Math.abs(a.y - b.y) < this.eps)
                continue;

            if (Math.abs(a.x - b.x) < this.eps) {
                ctx.fillRect((a.x - 0.4) * L, Math.min(a.y, b.y) * L, L * 0.8, Math.abs(a.y - b.y) * L );
            } else {
                ctx.fillRect(Math.min(a.x, b.x) * L, (a.y - 0.4) * L, Math.abs(a.x - b.x) * L, L * 0.8);
            }

        }

        ctx.fillStyle = "black";
        for (let i = 0; i < 2; i ++ ) {
            const eye_x = (this.cells[0].x + this.eye_dx[this.eye_direction][i] * 0.25) * L ;
            const eye_y = (this.cells[0].y + this.eye_dy[this.eye_direction][i] * 0.25) * L ;
            ctx.beginPath();
            ctx.arc(eye_x, eye_y, L * 0.15, 0, Math.PI * 2);
            ctx.fill();
        }
    }
}