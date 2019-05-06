/**
 * Created by Михаил on 19.03.16.
 */

function Finder() { //класc, в котором хранится наша текущая позиция
    this.depth = 0; //сколько папок мы прошли
    this.layout = null; //дерево менюшек
    this.currentLayout = null; //ссылка на узел в котором мы находимся
    this.folderIndexes = []; //путь до текущей позиции (индексы)
    this.folderNames = []; //путь до текущей позиции (названия)
    this.folderUrls = []; //путь до текущей позиции (адреса)
}

Finder.prototype.upFolders = function (count) {
    if (this.depth - count < 0) return;

    this.depth -= count;
    var i;
    for (i = 0; i < count; i++) {
        this.folderNames.pop();
        this.folderIndexes.pop();
    }

    this.currentLayout = this.layout;
    for (i = 1; i <= this.depth; i++) {
        this.currentLayout = this.currentLayout.menu[this.folderIndexes[i]];
    }
};

//Возвращает: нашелся ли путь
Finder.prototype.setFolderWithUrl = function (url) {
    var pos = [];
    pos[0] = 0;

    while (pos[0] !== layout.menu.length) {
        while (this.currentLayout.menu != null && this.currentLayout.menu[pos[this.depth]] != null) {

            if(url === this.currentLayout.url) {
                return true;
            }

            this.addFolder(pos[this.depth]);
            pos[this.depth] = 0;
        }

        if(url === this.currentLayout.url) {
            return true;
        }

        this.upFolders(1);

        pos[this.depth]++;
    }

    return false;
};

Finder.prototype.addFolder = function (index) {
    if (this.currentLayout.menu === undefined) return;

    this.addCurrentMenu(this.currentLayout.menu[index]);
};

Finder.prototype.getCurrentFolderName = function () {
    return this.folderNames[this.depth];
};

Finder.prototype.getDepth = function () {
    return this.depth;
};

Finder.prototype.setLayout = function (layout) {
    this.layout = layout;
    this.currentLayout = layout;
    this.depth = 0;
    this.folderIndexes = [];
    this.folderNames = [];
    this.folderIndexes.push(0);
    this.folderNames.push(layout.title);
};

Finder.prototype.addCurrentMenu = function (newCurrentLayout) {
    this.depth++;

    this.currentLayout = newCurrentLayout;
    this.folderIndexes.push(0);
    this.folderNames.push(this.currentLayout.title);
    this.folderUrls.push(this.currentLayout.url);
};

Finder.prototype.getCurrentMenu = function (layout) {
    return this.currentLayout;
};

Finder.prototype.getFoldersArray = function () {
    return this.folderNames;
};

Finder.prototype.setDepth = function (depth) {
    this.upFolders(this.depth - depth);
};

