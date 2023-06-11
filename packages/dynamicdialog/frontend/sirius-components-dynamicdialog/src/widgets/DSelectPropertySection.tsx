/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import { gql, useQuery } from '@apollo/client';
import FormControl from '@material-ui/core/FormControl';
import IconButton from '@material-ui/core/IconButton';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import Snackbar from '@material-ui/core/Snackbar';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import { useEffect, useState } from 'react';
import {
  DSelectPropertySectionProps,
  GQLDSelectWidgetQueryData,
  GQLDSelectWidgetQueryVariables,
  GQLObject,
} from './DSelectPropertySection.types';
// import { getTextDecorationLineValue } from './getTextDecorationLineValue';

// const useStyle = makeStyles<Theme, DSelectStyleProps>(() => ({
//   style: {
//     backgroundColor: ({ backgroundColor }) => (backgroundColor ? backgroundColor : 'inherit'),
//     color: ({ foregroundColor }) => (foregroundColor ? foregroundColor : 'inherit'),
//     fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
//     fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
//     fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
//     textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
//   },
// }));

const getCandidatesQuery = gql`
  query getCandidates(
    $editingContextId: ID!
    $dialogDescriptionId: ID!
    $widgetDescriptionId: ID!
    $variables: [Variable]
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        dynamicDialogQueryObjects(
          dialogDescriptionId: $dialogDescriptionId
          widgetDescriptionId: $widgetDescriptionId
          variables: $variables
        ) {
          id
          label
        }
      }
    }
  }
`;

export const DSelectPropertySection = ({
  dialogDescriptionId,
  widgetDescriptionId,
  outputVariableName,
  label,
  editingContextId,
  initialValue,
  setValue,
  inputVariables,
}: DSelectPropertySectionProps) => {
  // const props: DSelectStyleProps = {
  //   backgroundColor: widget.style?.backgroundColor ?? null,
  //   foregroundColor: widget.style?.foregroundColor ?? null,
  //   fontSize: widget.style?.fontSize ?? null,
  //   italic: widget.style?.italic ?? null,
  //   bold: widget.style?.bold ?? null,
  //   underline: widget.style?.underline ?? null,
  //   strikeThrough: widget.style?.strikeThrough ?? null,
  // };
  // const classes = useStyle(props);
  console.log(label);
  const [message, setMessage] = useState(null);
  const [isFocused, setFocus] = useState(false);
  const [candidates, setCandidates] = useState<GQLObject[]>(null);

  const onChange = (event) => {
    const newValue = event.target.value;
    setValue(outputVariableName, newValue);
  };

  const { loading, error, data } = useQuery<GQLDSelectWidgetQueryData, GQLDSelectWidgetQueryVariables>(
    getCandidatesQuery,
    { variables: { editingContextId, dialogDescriptionId, widgetDescriptionId, variables: inputVariables } }
  );
  useEffect(() => {
    if (!loading) {
      if (error) {
        setMessage('An unexpected error has occurred, please refresh the page');
      }
      const candidates = data?.viewer?.editingContext?.dynamicDialogQueryObjects;
      if (candidates) {
        setCandidates(candidates);
      }
    }
  }, [loading, error, data]);

  return (
    <FormControl error={!!message}>
      <Typography variant="subtitle2">{label}</Typography>
      <Select
        value={initialValue || ''}
        onChange={onChange}
        displayEmpty
        fullWidth
        data-testid={label}
        // inputProps={
        //   widget.style
        //     ? {
        //         className: classes.style,
        //       }
        //     : {}
        // }
      >
        <MenuItem
          value=""
          // classes={
          //   widget.style
          //     ? {
          //         root: classes.style,
          //       }
          //     : {}
          // }
        >
          <em>None</em>
        </MenuItem>
        {candidates?.map((option) => (
          <MenuItem
            value={option.id}
            key={option.id}
            // classes={
            //   widget.style
            //     ? {
            //         root: classes.style,
            //       }
            //     : {}
            // }
          >
            {option.label}
          </MenuItem>
        ))}
      </Select>
      {/* <FormHelperText>{widget.diagnostics[0]?.message}</FormHelperText> */}
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={!!message}
        autoHideDuration={3000}
        onClose={() => setMessage(null)}
        message={message}
        action={
          <IconButton size="small" aria-label="close" color="inherit" onClick={() => setMessage(null)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </FormControl>
  );
};
