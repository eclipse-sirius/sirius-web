/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { IconOverlay, getCSSColor, useSelection } from '@eclipse-sirius/sirius-components-core';
import {
  GQLWidget,
  PreviewWidgetComponent,
  PreviewWidgetProps,
  getTextDecorationLineValue,
} from '@eclipse-sirius/sirius-components-forms';
import { GQLReferenceWidget } from '@eclipse-sirius/sirius-components-widget-reference';
import Chip from '@material-ui/core/Chip';
import IconButton from '@material-ui/core/IconButton';
import InputAdornment from '@material-ui/core/InputAdornment';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import MoreHorizIcon from '@material-ui/icons/MoreHoriz';
import Autocomplete from '@material-ui/lab/Autocomplete';
import { useEffect, useRef, useState } from 'react';
import { GQLReferenceWidgetStyle } from './ReferenceWidgetFragment.types';

const isReferenceWidget = (widget: GQLWidget): widget is GQLReferenceWidget => widget.__typename === 'ReferenceWidget';

const useStyles = makeStyles<Theme, GQLReferenceWidgetStyle>((theme) => ({
  referenceValueStyle: {
    color: ({ color }) => (color ? getCSSColor(color, theme) : undefined),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : undefined),
    fontStyle: ({ italic }) => (italic ? 'italic' : 'unset'),
    fontWeight: ({ bold }) => (bold ? 'bold' : 'unset'),
    textDecorationLine: ({ underline, strikeThrough }) =>
      getTextDecorationLineValue(underline ?? null, strikeThrough ?? null),
  },
  selected: {
    color: theme.palette.primary.main,
  },
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  propertySection: {
    overflowX: 'hidden',
  },
}));

export const ReferencePreview: PreviewWidgetComponent = ({ widget }: PreviewWidgetProps) => {
  let style: GQLReferenceWidgetStyle | null = null;
  if (isReferenceWidget(widget)) {
    const referenceWidget: GQLReferenceWidget = widget;
    style = referenceWidget.style;
  }
  const props: GQLReferenceWidgetStyle = {
    color: style?.color ?? null,
    fontSize: style?.fontSize ?? null,
    italic: style?.italic ?? null,
    bold: style?.bold ?? null,
    underline: style?.underline ?? null,
    strikeThrough: style?.strikeThrough ?? null,
  };
  const classes = useStyles(props);

  const [selected, setSelected] = useState<boolean>(false);
  const { selection } = useSelection();
  const ref = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setSelected(true);
    } else {
      setSelected(false);
    }
  }, [selection, widget]);

  if (!isReferenceWidget(widget)) {
    return null;
  }

  const options = [{ label: 'Referenced Value', iconURL: '/api/images/icons/full/obj16/Entity.svg' }];
  return (
    <div className={classes.propertySection}>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
      <Autocomplete
        data-testid={widget.label}
        multiple
        filterSelectedOptions
        disabled={false}
        open={false}
        loading={false}
        options={options}
        value={options}
        disableClearable
        renderTags={(value, getTagProps) =>
          value.map(({ label, iconURL }, index) => (
            <Chip
              key={index}
              classes={{ label: classes.referenceValueStyle }}
              label={label}
              data-testid={`reference-value-${label}`}
              icon={
                <div>
                  <IconOverlay iconURL={[iconURL]} alt={''} />
                </div>
              }
              {...getTagProps({ index })}
            />
          ))
        }
        renderInput={(params) => (
          <TextField
            inputRef={ref}
            {...params}
            variant="standard"
            error={widget.diagnostics.length > 0}
            helperText={widget.diagnostics[0]?.message}
            InputProps={{
              readOnly: true,
              ...params.InputProps,
              endAdornment: (
                <>
                  {params.InputProps.endAdornment}
                  <InputAdornment position="end" className={classes.endAdornmentButton}>
                    <IconButton
                      aria-label="edit"
                      size="small"
                      title="Edit"
                      disabled={false}
                      data-testid={`${widget.label}-more`}>
                      <MoreHorizIcon />
                    </IconButton>
                    <IconButton
                      aria-label="add"
                      size="small"
                      title="Create an object"
                      disabled={false}
                      data-testid={`${widget.label}-add`}>
                      <AddIcon />
                    </IconButton>
                    <IconButton
                      aria-label="clear"
                      size="small"
                      title="Clear"
                      disabled={false}
                      data-testid={`${widget.label}-clear`}>
                      <DeleteIcon />
                    </IconButton>
                  </InputAdornment>
                </>
              ),
            }}
          />
        )}
      />
    </div>
  );
};
