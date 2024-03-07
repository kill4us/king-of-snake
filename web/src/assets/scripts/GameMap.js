import { gameObject } from "./gameObject";
import { Snake } from "./Snake";
import { Wall } from "./Wall";

export class GameMap extends gameObject {
    constructor(ctx, parent, store) {
        super();

        this.ctx = ctx;
        this.parent = parent;
        this.store = store;
        this.L = 0;

        this.rows = 13;
        this.cols = 14;
        

        this.inner_walls_count = 20;
        this.walls = [];

        this.snakes = [
            new Snake({id: 0, color: "#4876EC", r: this.rows - 2, c: 1}, this),
            new Snake({id: 1, color: "#F94848", r: 1, c: this.cols - 2}, this),
        ]
    }



    create_walls() {
        const g = this.store.state.pk.gamemap;
        
        for (let r = 0; r < this.rows; r ++ ) {
            for (let c = 0; c < this.cols; c ++ ) {
                if (g[r][c])
                {
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }

        return true;
    }
                                                                                
    add_listening_events() {
        this.ctx.canvas.focus();

        
        this.ctx.canvas.addEventListener("keydown", e => {
            let d = -1;  //  移动的方向
            if (e.key === 'w') d = 0;
            else if (e.key === 'd') d = 1;
            else if (e.key === 's') d = 2;
            else if (e.key === 'a') d = 3;

            if (d >= 0) {
                //  合法的移动操作
                //  向后端发送移动请求
                this.store.state.pk.socket.send(JSON.stringify({
                    event: "move",
                    direction: d,

                }));
            }
        });
    }

    start() {
        this.create_walls();
        this.add_listening_events();
    }

    update_size() {  //  实时根据窗口大小更新地图大小
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }

    next_round() {  //  让两条蛇都进入下一个回合
        for (const snake of this.snakes) {
            snake.next_step();
        }
    }

    update() {
        this.update_size();
        if (this.check_ready()) {
            this.next_round();
        }
        this.render();
    }
    //  判断两只蛇有没有准备好下一步的操作
    check_ready() {  //  当两只蛇都静止，并且都获取了下一步的操作，才表示准备好了
        for (const snake of this.snakes) {
            if (snake.status !== "idle") return false;  //  当前蛇不是静止的，所以return false
            if (snake.direction === -1) return false;  //  当前蛇还没有获取下一步的操作指令,return false

        }
        return true;
    }

    check_valid(cell) {  //  检测目标位置是否合法，障碍物、自己的身体，后退，对方的身体均为不合法
        for (const wall of this.walls) {  //  判断是否撞墙
            if (wall.r === cell.r && wall.c === cell.c) {
                return false;
            }
        }

        for (const snake of this.snakes) {
            let k = snake.cells.length;
            if (!snake.check_tail_increasing()) {  //  当蛇尾会往前进时，就不用判断蛇尾
                k --;
            }
            for (let i = 0; i < k; i ++ ) {  //  是否撞到蛇身体
                if (snake.cells[i].r === cell.r && snake.cells[i].c === cell.c) {
                    return false;
                }
            }
        }
        return true;
    }

    render() {
        const color_even = "#AAD751", color_odd = "#A2D149";
        for (let r = 0; r < this.rows; r ++ ) {
            for (let c = 0; c < this.cols; c ++ ) {
                if ((r + c) % 2 == 0) {
                    this.ctx.fillStyle = color_even;
                } else {
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);
            }
        }
    }

    }
