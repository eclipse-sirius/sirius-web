/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import AccountTreeIcon from '@material-ui/icons/AccountTree';
import ArrowDropDownCircleIcon from '@material-ui/icons/ArrowDropDownCircle';
import BarChartIcon from '@material-ui/icons/BarChart';
import CheckBoxIcon from '@material-ui/icons/CheckBox';
import FormatListBulletedIcon from '@material-ui/icons/FormatListBulleted';
import ImageIcon from '@material-ui/icons/Image';
import LabelOutlinedIcon from '@material-ui/icons/LabelOutlined';
import LinearScaleOutlinedIcon from '@material-ui/icons/LinearScaleOutlined';
import LinkIcon from '@material-ui/icons/Link';
import PieChartIcon from '@material-ui/icons/PieChart';
import RadioButtonCheckedIcon from '@material-ui/icons/RadioButtonChecked';
import TextFieldsIcon from '@material-ui/icons/TextFields';
import TextFormatIcon from '@material-ui/icons/TextFormat';
import ViewColumnIcon from '@material-ui/icons/ViewColumn';
import { WidgetDescriptor } from './FormDescriptionEditorRepresentation.types';
import { Button } from './icons/Button';

export const coreWidgets: WidgetDescriptor[] = [
  {
    name: 'BarChart',
    label: 'Bar Chart',
    icon: <BarChartIcon />,
  },
  {
    name: 'Button',
    icon: <Button width={'24px'} height={'24px'} color={'secondary'} />,
  },
  {
    name: 'Checkbox',
    icon: <CheckBoxIcon />,
  },
  {
    name: 'FlexboxContainer',
    label: 'Flexbox Container',
    icon: <ViewColumnIcon width={'24px'} height={'24px'} color={'secondary'} />,
  },
  {
    name: 'Image',
    icon: <ImageIcon width={'24px'} height={'24px'} color={'secondary'} />,
  },
  {
    name: 'Label',
    icon: <LabelOutlinedIcon />,
  },
  {
    name: 'Link',
    icon: <LinkIcon />,
  },
  {
    name: 'List',
    icon: <FormatListBulletedIcon />,
  },
  {
    name: 'MultiSelect',
    icon: <ArrowDropDownCircleIcon />,
  },
  {
    name: 'PieChart',
    label: 'Pie Chart',
    icon: <PieChartIcon />,
  },
  {
    name: 'Radio',
    icon: <RadioButtonCheckedIcon />,
  },
  {
    name: 'RichText',
    icon: <TextFormatIcon />,
  },
  {
    name: 'Select',
    icon: <ArrowDropDownCircleIcon />,
  },
  {
    name: 'Slider',
    icon: <LinearScaleOutlinedIcon />,
  },
  {
    name: 'Tree',
    icon: <AccountTreeIcon />,
  },
  {
    name: 'SplitButton',
    label: 'Split Button',
    icon: <Button width={'24px'} height={'24px'} color={'secondary'} />,
  },
  {
    name: 'TextArea',
    label: 'Text Area',
    icon: <TextFieldsIcon />,
  },
  {
    name: 'Textfield',
    icon: <TextFieldsIcon />,
  },
];
